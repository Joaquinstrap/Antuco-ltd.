package com.example.antuco.usuarios.dto;

import lombok.Data;

@Data
public class RegistroRequest {
    private String username;
    private String password;
    private String rol; // Opcional, por defecto podría ser USER
}