package com.example.antuco.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.antuco.inventario.model.Inventariomodel;


@Repository
public interface InventarioRepository extends JpaRepository<Inventariomodel, Long> {

    //El producto del inventario lo buscariamos en base al productoId
    Optional<Inventariomodel> findByProductoId(Long productoId);

}
