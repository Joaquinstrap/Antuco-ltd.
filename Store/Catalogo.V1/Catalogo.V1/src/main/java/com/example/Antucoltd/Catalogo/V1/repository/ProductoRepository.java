package com.example.Antucoltd.Catalogo.V1.repository;

import org.springframework.stereotype.Repository;
import com.example.Antucoltd.Catalogo.V1.model.Productosdelcatalogo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;



@Repository
public interface ProductoRepository extends JpaRepository<Productosdelcatalogo, Long> {
    

    List<Productosdelcatalogo> findByCategoriaId(String id);



}
