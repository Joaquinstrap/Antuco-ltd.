package com.example.antuco.carrito.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgregarItemRequest {
    private String usuarioId;
    private Long productoId;
    private String nombreProducto; // Viene del frontend o del catalogo
    private BigDecimal precioUnitario; // Viene del catalogo
    private String imagenUrl;
    private Integer cantidad;
}