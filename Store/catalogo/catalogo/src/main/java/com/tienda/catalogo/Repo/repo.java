package com.tienda.catalogo.Repo;

import org.springframework.stereotype.Repository;
import com.tienda.catalogo.Productos.Ropa;
import org.springframework.data.jpa.repository.JpaRepository;

// este es el repositorio
@Repository
public interface repo extends JpaRepository<Ropa, Long> {
    

}
