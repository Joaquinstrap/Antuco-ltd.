package com.example.antuco.usuarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "El ID del usuario", example = "001")
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(description = "el nombre de usuario", example = "vegetta777")
    private String username; // Usaremos esto como el ID para el carrito (ej: email)

    @Column(nullable = false)
    @Schema(description = "La clave del usuario", example = "Servicios.200")
    private String clave;
    
    @Schema(description = "El rol del usuario", example = "ADMIN")
    private String rol; // Ej: "USER", "ADMIN"
}
