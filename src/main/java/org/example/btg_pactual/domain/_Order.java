package org.example.btg_pactual.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "orders")
public class _Order {

    @Id
    private String id;

    private Long codigoPedido;

    private Long codigoCliente;

    private List<_OrderItem> itens;

    public Long getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(Long codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Long getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Long codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<_OrderItem> getItens() {
        return itens;
    }

    public void setItens(List<_OrderItem> itens) {
        this.itens = itens;
    }
}
