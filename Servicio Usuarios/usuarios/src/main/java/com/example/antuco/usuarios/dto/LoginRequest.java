package com.example.antuco.usuarios.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String clave;
}
