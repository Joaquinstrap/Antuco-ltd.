package com.example.antuco.usuarios.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// SUPER IMPORTANTE!!!!!
@Entity
@Table(name = "almacen_usuario")
public class Usuario {

    @NotNull(message = "Se necesita tener una id correcta")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre debe ser correcto")
    @Column(nullable = false)
    private String nombre_usuario;

    @NotBlank(message = "El email es obligatorio")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(nullable = false)
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime fechaRegistro;

    // CONSTRUCTORES
    public Usuario() {
    }

    public Usuario(String nombre_usuario, String email, String password) {
        this.nombre_usuario = nombre_usuario;
        this.email = email;
        this.password = password;
    }
}
