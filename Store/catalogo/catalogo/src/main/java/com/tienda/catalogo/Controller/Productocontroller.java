package com.tienda.catalogo.Controller;
import com.tienda.catalogo.Productos.Ropa;
import com.tienda.catalogo.Repo.repo;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Productos")
public class Productocontroller {

    @Autowired
    private repo merch;


    @PostMapping
    public ResponseEntity<Ropa> crearRopa(@RequestBody @Valid Ropa ropa){

        Ropa ropanueva =  merch.save(ropa);
        return ResponseEntity.ok(ropanueva);
    }
    

    

}
