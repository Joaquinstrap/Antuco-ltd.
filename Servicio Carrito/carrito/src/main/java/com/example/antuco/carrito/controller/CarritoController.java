package com.example.antuco.carrito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.antuco.carrito.dto.AgregarItemRequest;
import com.example.antuco.carrito.model.Carrito;
import com.example.antuco.carrito.service.CarritoService;

@RestController
@RequestMapping("/api/V1/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // Endpoint para ver el carrito de un usuario
    @GetMapping("/{usuarioId}")
    public ResponseEntity<Carrito> verCarrito(@PathVariable String usuarioId) {
        Carrito carrito = carritoService.obtenerCarritoPorUsuario(usuarioId);
        return ResponseEntity.ok(carrito);
    }

    // Endpoint para agregar un producto
    @PostMapping("/agregar")
    public ResponseEntity<Carrito> agregarAlCarrito(@RequestBody AgregarItemRequest request) {
        // Aquí ideally validarías que el request.getUsuarioId() no sea nulo
        Carrito carritoActualizado = carritoService.agregarItem(request);
        return ResponseEntity.ok(carritoActualizado);
    }

    // Endpoint para eliminar un item
    @DeleteMapping("/{carritoId}/item/{itemId}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long carritoId, @PathVariable Long itemId) {
        carritoService.eliminarItem(carritoId, itemId);
        return ResponseEntity.noContent().build();
    }
}