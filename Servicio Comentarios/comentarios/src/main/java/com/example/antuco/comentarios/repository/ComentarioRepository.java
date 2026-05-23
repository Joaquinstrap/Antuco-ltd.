package com.example.antuco.comentarios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.antuco.comentarios.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    // Buscar comentarios de un producto específico, ordenados por fecha (más recientes primero)
    List<Comentario> findByProductoIdOrderByFechaCreacionDesc(Long productoId);
}