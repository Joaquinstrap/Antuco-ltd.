package com.example.antuco.eventos.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CrearEventoRequest {
    private String nombre;
    private LocalDateTime fecha;
    private String lugar;
    private Double precio;
    private Integer capacidadTotal;
}