package com.example.antuco.usuarios.dto;

import lombok.Data;

@Data
public class RegistroRequest {
    private String username;
    private String clave;
    private String rol; // Opcional, por defecto podría ser USER
}
