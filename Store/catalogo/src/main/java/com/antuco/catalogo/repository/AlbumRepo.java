package com.antuco.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antuco.catalogo.model.Album;

public interface AlbumRepo extends JpaRepository<Album, Long>{
}
