package org.example.btg_pactual.factory;

import org.example.btg_pactual.domain.Order;
import org.example.btg_pactual.domain.OrderItem;
import org.example.btg_pactual.utils.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;

public class OrderEntityFactory {
    public static Order build(){
        var items = new OrderItem("notebook", 1, BigDecimal.valueOf(20.50));

        var entity = new Order();
        entity.setCodigoPedido(1L);
        entity.setCodigoCliente(2L);
        entity.setTotal(BigDecimal.valueOf(20.50));
        entity.setItens(List.of(items));

        return entity;
    }

    public static Page<Order> buildWithPage(){
        return new PageImpl<>(List.of(build()));
    }
}
