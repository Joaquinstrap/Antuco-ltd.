package com.grupodos.antuco.Catalogo.V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupodos.antuco.Catalogo.V1.model.Productosdelcatalogo;



@Repository
public interface ProductoRepository extends JpaRepository<Productosdelcatalogo, Long> {
    

    List<Productosdelcatalogo> findByCategoriaId(String id);



}
