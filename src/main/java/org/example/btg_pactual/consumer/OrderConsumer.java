package org.example.btg_pactual.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.btg_pactual.domain._Order;
import org.example.btg_pactual.service.OrderService;
import org.example.btg_pactual.utils.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.example.btg_pactual.infra.RabbitMQ.queueName;

@Component
public class OrderConsumer {

    private final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    public OrderConsumer(ObjectMapper objectMapper, OrderService orderService) {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

//    @RabbitListener(queues = queueName)
//    public void listenOrderQueue(@Payload String orderJson) throws JsonProcessingException {
//        _Order order = objectMapper.readValue(orderJson, _Order.class);
//        orderService.saveOrder(order);
//    }

    @RabbitListener(queues = queueName)
    public void listenOrderQueue(Message<OrderCreatedEvent> message)  {
        logger.info("Message consumed: {}", message);

        orderService.save(message.getPayload());

    }
}
