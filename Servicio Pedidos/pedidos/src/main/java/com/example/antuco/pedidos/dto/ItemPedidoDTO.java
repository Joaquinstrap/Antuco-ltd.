package com.example.antuco.pedidos.dto;

import lombok.Data;

@Data
public class ItemPedidoDTO {
    private Long productoId;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
}