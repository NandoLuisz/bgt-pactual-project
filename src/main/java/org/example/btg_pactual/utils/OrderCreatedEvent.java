package org.example.btg_pactual.utils;

import java.util.List;

public record OrderCreatedEvent(Long codigoPedido, Long codigoCliente, List<OrderItemsEvent> itens) {
}
