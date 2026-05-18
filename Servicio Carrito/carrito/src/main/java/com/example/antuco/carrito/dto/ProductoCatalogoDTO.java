// esto es solo para que lea lo que dice el microservicio de catalogo

package com.example.antuco.carrito.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCatalogoDTO {
    private Long id; // El JSON de catálogo probablemente lo mande como "id"

    // AQUÍ ESTÁ EL CAMBIO: Le decimos que en el JSON se llama "nombre_producto"
    @JsonProperty("nombre_producto")
    private String nombre;

    // AQUÍ ESTÁ EL CAMBIO: Le decimos que en el JSON se llama "precio"
    @JsonProperty("precio")
    private Double precio;
}