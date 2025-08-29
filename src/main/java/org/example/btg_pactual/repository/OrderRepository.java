package org.example.btg_pactual.repository;

import org.example.btg_pactual.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OrderRepository extends MongoRepository<Order, String> {
    Page<Order> findAllByCodigoCliente(Long codigoCliente, PageRequest pageRequest);
//    Optional<_Order> findByCodigoPedido(Long codigoPedido);

}
