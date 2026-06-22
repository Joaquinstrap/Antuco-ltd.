package com.example.antuco.eventos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservarRequest {
    @NotNull(message = "ID del evento requerido")
    private Long eventoId;
    
    @NotNull(message = "Cantidad requerida")
    @Min(value = 1, message = "Mínimo 1 entrada")
    private Integer cantidad;
}