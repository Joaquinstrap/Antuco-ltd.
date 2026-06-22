package com.example.antuco.comentarios.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrearComentarioRequest {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @NotBlank(message = "El comentario no puede estar vacío")
    private String texto;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "Mínimo 1 estrella")
    @Max(value = 5, message = "Máximo 5 estrellas")
    private Integer calificacion;
    
    // En un sistema con JWT real, el username vendría del token.
    // Como es académico, lo pasamos en el body o header. Aquí lo asumimos inyectado.
    // Pero para simplificar la prueba desde Postman, lo agregaremos en el controller.
}