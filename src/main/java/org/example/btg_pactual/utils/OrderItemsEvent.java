package org.example.btg_pactual.utils;

import java.math.BigDecimal;

public record OrderItemsEvent(String produto, Integer quantidade, BigDecimal preco) {
}
