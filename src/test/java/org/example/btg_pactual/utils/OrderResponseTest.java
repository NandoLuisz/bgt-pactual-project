package org.example.btg_pactual.utils;

import org.example.btg_pactual.factory.OrderEntityFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseTest {
    @Nested
    class FromEntity{
        @Test
        void shouldMapCorrectly(){
            var input = OrderEntityFactory.build();

            var output = OrderResponse.fromEntity(input);

            assertEquals(input.getCodigoCliente(), output.codigoCliente());
            assertEquals(input.getCodigoPedido(), output.codigoPedido());
            assertEquals(input.getTotal(), output.total());
        }
    }
}