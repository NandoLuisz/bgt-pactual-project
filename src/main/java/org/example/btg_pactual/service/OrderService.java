package org.example.btg_pactual.service;

import org.bson.Document;
import org.example.btg_pactual.domain.Order;
import org.example.btg_pactual.domain.OrderItem;
import org.example.btg_pactual.repository.OrderRepository;
import org.example.btg_pactual.utils.OrderCreatedEvent;
import org.example.btg_pactual.utils.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final MongoTemplate mongoTemplate;

    public OrderService(OrderRepository orderRepository, MongoTemplate mongoTemplate) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(OrderCreatedEvent event){
        Order newOrder = new Order();
        newOrder.setCodigoCliente(event.codigoCliente());
        newOrder.setCodigoPedido(event.codigoPedido());
        newOrder.setItens(getOrderItems(event));
        newOrder.setTotal(getTotal(event));
        orderRepository.save(newOrder);
    }

    public Page<OrderResponse> findAllByCustomerId(Long codigoCliente, PageRequest pageRequest){
        var orders = orderRepository.findAllByCodigoCliente(codigoCliente, pageRequest);
        return orders.map(OrderResponse::fromEntity);

    }

    public BigDecimal findTotalOnOrdersByCustomerId(Long codigoCliente){
        var aggregations = newAggregation(
                match(Criteria.where("codigoCliente").is(codigoCliente)),
                group().sum("total").as("total")
        );
        var response = mongoTemplate.aggregate(aggregations, "tb_orders", Document.class);

        return new BigDecimal(response.getUniqueMappedResult().get("total").toString());
    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.itens()
                .stream()
                .map(item -> item.preco().multiply(BigDecimal.valueOf(item.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.itens().stream().
                map(item -> new OrderItem(item.produto(), item.quantidade(), item.preco())).toList();
    }

}
