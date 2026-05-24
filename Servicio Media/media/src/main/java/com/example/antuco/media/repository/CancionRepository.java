package com.example.antuco.media.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.antuco.media.model.Cancion;

public interface CancionRepository extends JpaRepository<Cancion, Long> {
    // Buscar todas las canciones de un álbum
    List<Cancion> findByIdAlbum(String idAlbum);
}