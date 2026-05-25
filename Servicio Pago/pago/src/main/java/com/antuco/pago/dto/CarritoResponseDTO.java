package com.antuco.pago.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CarritoResponseDTO {
    private Long id; // Este será nuestro idCarro
    private String usuarioId;
    private java.util.List<ItemCarritoResponseDTO> items;
    
    @Data
    public static class ItemCarritoResponseDTO {
        private Long id;
        private Long productoId;
        private String nombreProducto;
        private BigDecimal precioUnitario;
        private Integer cantidad;
    }
}