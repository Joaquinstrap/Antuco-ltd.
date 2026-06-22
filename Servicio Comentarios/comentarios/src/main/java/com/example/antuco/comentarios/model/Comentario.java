package com.example.antuco.comentarios.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "comentarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId; // No es FK real en BD para evitar acoplamiento, pero lógicamente lo es
    private String usuarioUsername;
    
    @Column(columnDefinition = "TEXT")
    private String texto;
    
    private Integer calificacion; // Ej: 5

    @CreationTimestamp
    private LocalDateTime fechaCreacion;
}