package com.antuco.catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antuco.catalogo.dto.ProductoDTO;
import com.antuco.catalogo.model.Producto;
import com.antuco.catalogo.service.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> getAllProducts() {
        return ResponseEntity.ok(productoService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductoDTO dto) {
        try {
            Producto created = productoService.createProducto(dto);
            return ResponseEntity.ok(created);
        } catch (RuntimeException e) {
            // This catches our "Album not found" or "Must link to something" errors
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
