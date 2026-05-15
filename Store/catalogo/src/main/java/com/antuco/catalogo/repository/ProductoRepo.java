package com.antuco.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.antuco.catalogo.model.Producto;

@Repository
public interface ProductoRepo extends JpaRepository<Producto, Long> {    
}