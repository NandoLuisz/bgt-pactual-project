package org.example.btg_pactual.controller;

import org.example.btg_pactual.domain._Order;
import org.example.btg_pactual.service.OrderService;
import org.example.btg_pactual.utils.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//    @GetMapping
//    public ResponseEntity<List<_Order>> listAllOrder(){
//        return ResponseEntity.ok(orderService.listAllOrders());
//    }

//    @GetMapping("/totalValue/{codigoPedido}")
//    public ResponseEntity<TotalValueOrderResponseDTO> TotalValueOrder(@PathVariable(value = "codigoPedido") Long codigoPedido){
//        var totalValue = orderService.totalValue(codigoPedido);
//        return ResponseEntity.ok(new TotalValueOrderResponseDTO(totalValue));
//    }

//    @GetMapping("/amountOrder/{codigoCliente}")
//    public ResponseEntity<AmountOrderResponseDTO> AmountOrder(@PathVariable(value = "codigoCliente") Long codigoCliente){
//        var totalValue = orderService.amountOrder(codigoCliente);
//        return ResponseEntity.ok(new AmountOrderResponseDTO(totalValue));
//    }

//    @GetMapping("/list/{codigoCliente}")
//    public ResponseEntity<List<OrderItemResponseDTO>> listOrderCustomer(@PathVariable(value = "codigoCliente") Long codigoCliente){
//        var listOrder = orderService.listOrderCustomer(codigoCliente);
//        List<OrderItemResponseDTO> listOrderResponse = listOrder.stream().map(orderItem -> new OrderItemResponseDTO(orderItem.getProduto(), orderItem.getQuantidade(), orderItem.getPreco())).toList();
//        return ResponseEntity.ok(listOrderResponse);
//    }

    @GetMapping("/customer/{codigoCliente}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@PathVariable(name = "codigoCliente") Long codigoCliente,
                                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){

        var pageResponse = orderService.findAllByCustomerId(codigoCliente, PageRequest.of(page, pageSize));
        var totalOnOrders = orderService.findTotalOnOrdersByCustomerId(codigoCliente);

        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOnOrders", totalOnOrders),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }

}
