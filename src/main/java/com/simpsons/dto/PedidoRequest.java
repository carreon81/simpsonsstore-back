package com.simpsons.dto;

import java.util.List;

public class PedidoRequest {
    private List<PedidoItem> items;

    public PedidoRequest() {}

    public List<PedidoItem> getItems() {
        return items;
    }

    public void setItems(List<PedidoItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PedidoRequest{items=" + items + "}";
    }
}
