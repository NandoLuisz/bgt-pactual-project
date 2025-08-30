package org.example.btg_pactual.factory;

import org.example.btg_pactual.utils.OrderCreatedEvent;
import org.example.btg_pactual.utils.OrderItemsEvent;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreatedEventFactory {
    public static OrderCreatedEvent buildWithOneItem(){

        var item = new OrderItemsEvent("notebook", 1, BigDecimal.valueOf(20.50));
        return new OrderCreatedEvent(1L, 2L, List.of(item));

    }

    public static OrderCreatedEvent buildWithTwoItem(){

        var item1 = new OrderItemsEvent("notebook", 1, BigDecimal.valueOf(20.50));
        var item2 = new OrderItemsEvent("mouse", 1, BigDecimal.valueOf(35.25));
        return new OrderCreatedEvent(1L, 2L, List.of(item1, item2));

    }

}
