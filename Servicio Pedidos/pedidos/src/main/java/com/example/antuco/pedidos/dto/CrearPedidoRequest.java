package com.example.antuco.pedidos.dto;

import java.util.List;

import lombok.Data;

@Data
public class CrearPedidoRequest {
    private String usuarioUsername;
    private List<ItemPedidoDTO> items;
}