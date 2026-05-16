package com.example.antuco.catalogo.repository;

import org.springframework.stereotype.Repository;
import com.example.antuco.catalogo.model.Productosdelcatalogo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;



@Repository
public interface ProductoRepository extends JpaRepository<Productosdelcatalogo, Long> {
    

    List<Productosdelcatalogo> findByCategoriaId(String id);



}
