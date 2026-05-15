package com.antuco.catalogo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO { // Este paquete es necesario para procesar el JSON del producto. y tambien la condicional de album/merch
    
    @NotBlank(message = "Dato vacio o mal escrito")
    private String sku;

    @NotNull(message = "Dato vacio o mal escrito")
    @Positive(message = "Precio debe ser numero positivo")
    private BigDecimal precio;

    @NotNull(message = "Dato vacio o mal escrito")
    @PositiveOrZero(message = "No se puede tener stock negativo")
    private Integer stock;

    // El sagrado JSON de producto
    private String especificacion;

    // Mandar UNO SOLO al JSON de postman
    private Long albumId;
    private Long merchId;
}
