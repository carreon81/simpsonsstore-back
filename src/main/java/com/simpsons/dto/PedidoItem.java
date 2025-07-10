package com.simpsons.dto;

public class PedidoItem {
    private Long productoId;
    private Integer cantidad;

    public PedidoItem() {} // Constructor vacío necesario

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "PedidoItem{productoId=" + productoId + ", cantidad=" + cantidad + "}";
    }
}
