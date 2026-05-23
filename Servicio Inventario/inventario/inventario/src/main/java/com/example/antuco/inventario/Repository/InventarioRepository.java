package com.example.antuco.inventario.Repository;

import com.example.antuco.inventario.Model.Inventariomodel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InventarioRepository extends JpaRepository<Inventariomodel, Long> {

    //El producto del inventario lo buscariamos en base al productoId
    Optional<Inventariomodel> findByProductoId(Long productoId);

}
