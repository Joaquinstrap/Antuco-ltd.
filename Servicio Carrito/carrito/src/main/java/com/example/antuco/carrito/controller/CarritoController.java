package com.example.antuco.carrito.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.antuco.carrito.model.Carrito;
import com.example.antuco.carrito.service.CarritoService;

@RestController
@RequestMapping("/api/V1/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Carrito> verCarrito(@PathVariable String usuarioId) {
        Carrito carrito = carritoService.obtenerCarritoPorUsuario(usuarioId);
        return ResponseEntity.ok(carrito);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Carrito> agregarAlCarrito(@RequestBody com.example.antuco.carrito.dto.AgregarItemRequest request) {
        Carrito carritoActualizado = carritoService.agregarItem(request);
        return ResponseEntity.ok(carritoActualizado);
    }

    @PostMapping("/agregar-simple")
    public ResponseEntity<String> agregarSimple(@RequestBody Map<String, Object> payload) {
        String usuarioId = (String) payload.get("usuarioId");
        Long productoId = Long.valueOf(payload.get("productoId").toString());
        Integer cantidad = Integer.valueOf(payload.get("cantidad").toString());

        carritoService.agregarItemSimple(usuarioId, productoId, cantidad);
        return ResponseEntity.ok("Agregado desde catálogo con éxito");
    }

    @DeleteMapping("/{carritoId}/item/{itemId}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long carritoId, @PathVariable Long itemId) {
        carritoService.eliminarItem(carritoId, itemId);
        return ResponseEntity.noContent().build();
    }
}