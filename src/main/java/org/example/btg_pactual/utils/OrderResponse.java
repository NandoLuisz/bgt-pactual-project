package org.example.btg_pactual.utils;

import org.example.btg_pactual.domain.Order;

import java.math.BigDecimal;

public record OrderResponse(Long codigoPedido, Long codigoCliente, BigDecimal total) {
    public static OrderResponse fromEntity(Order entity){
        return new OrderResponse(entity.getCodigoPedido(), entity.getCodigoCliente(), entity.getTotal());
    }
}
