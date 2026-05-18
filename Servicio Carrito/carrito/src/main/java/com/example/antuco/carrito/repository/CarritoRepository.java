package com.example.antuco.carrito.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.antuco.carrito.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    // Método mágico de Spring Data JPA: busca por el campo usuarioId
    Optional<Carrito> findByUsuarioId(String usuarioId);
}