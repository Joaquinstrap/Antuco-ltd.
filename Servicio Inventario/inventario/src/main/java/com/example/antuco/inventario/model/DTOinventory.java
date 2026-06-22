package com.example.antuco.inventario.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class DTOinventory {


    @NotNull(message = "El productoId no puede ser nulo")
    @Min(value = 1, message = "El productoId no puede ser menor que 1")
    private Long productoId;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer cantidad;


    @NotBlank(message = "El tipo de producto no puede estar vacío")
    private String tipoProducto;




}
