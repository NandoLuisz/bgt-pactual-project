package org.example.btg_pactual.controller;

import org.example.btg_pactual.factory.OrderResponseFactory;
import org.example.btg_pactual.service.OrderService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    OrderService orderService;

    @InjectMocks
    OrderController orderController;

    @Captor
    ArgumentCaptor<Long> codigoClienteCaptor;

    @Captor
    ArgumentCaptor<PageRequest> pageRequestCaptor;

    @Nested
    class ListOrders{

        @Test
        void shouldReturnHttpOk() {
            var codigoCliente = 1L;
            var page = 0;
            var pageSize = 10;

            doReturn(OrderResponseFactory.buildWithOneItem())
                    .when(orderService).findAllByCustomerId(anyLong(), any());
            doReturn(BigDecimal.valueOf(20.50))
                    .when(orderService).findTotalOnOrdersByCustomerId(anyLong());

            var response = orderController.listOrders(codigoCliente, page, pageSize);

            assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        }

        @Test
        void shouldPassCorrectParametersToService() {
            var codigoCliente = 1L;
            var page = 0;
            var pageSize = 10;

            doReturn(OrderResponseFactory.buildWithOneItem())
                    .when(orderService).findAllByCustomerId(codigoClienteCaptor.capture(), pageRequestCaptor.capture());
            doReturn(BigDecimal.valueOf(20.50))
                    .when(orderService).findTotalOnOrdersByCustomerId(codigoClienteCaptor.capture());

            var response = orderController.listOrders(codigoCliente, page, pageSize);

            assertEquals(2, codigoClienteCaptor.getAllValues().size());
            assertEquals(codigoCliente, codigoClienteCaptor.getAllValues().get(0));
            assertEquals(codigoCliente, codigoClienteCaptor.getAllValues().get(1));
            assertEquals(page, pageRequestCaptor.getValue().getPageNumber());
            assertEquals(pageSize, pageRequestCaptor.getValue().getPageSize());
        }

        @Test
        void shouldReturnResponseBodyCorrectly() {
            var codigoCliente = 1L;
            var page = 0;
            var pageSize = 10;
            var totalOnOrders = BigDecimal.valueOf(20.50);
            var pagination = OrderResponseFactory.buildWithOneItem();

            doReturn(pagination)
                    .when(orderService).findAllByCustomerId(anyLong(), any());
            doReturn(totalOnOrders)
                    .when(orderService).findTotalOnOrdersByCustomerId(anyLong());

            var response = orderController.listOrders(codigoCliente, page, pageSize);

            assertNotNull(response);
            assertNotNull(response.getBody());
            assertNotNull(response.getBody().data());
            assertNotNull(response.getBody().pagination());
            assertNotNull(response.getBody().summary());

            assertEquals(totalOnOrders, response.getBody().summary().get("totalOnOrders"));

            assertEquals(pagination.getTotalElements(), response.getBody().pagination().totalElements());
            assertEquals(pagination.getTotalPages(), response.getBody().pagination().totalPages());
            assertEquals(pagination.getNumber(), response.getBody().pagination().page());
            assertEquals(pagination.getSize(), response.getBody().pagination().pageSize());

            assertEquals(pagination.getContent(), response.getBody().data());
        }
    }
}