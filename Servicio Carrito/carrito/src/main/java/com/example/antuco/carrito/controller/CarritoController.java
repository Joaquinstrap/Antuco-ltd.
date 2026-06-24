package com.example.antuco.carrito.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.antuco.carrito.model.Carrito;
import com.example.antuco.carrito.service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/V1/carrito")
@Tag(name = "Carrito", description = "Funciones CRUD del carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/{usuarioId}")
    @Operation(summary = "Ver carrito", description = "Listar el carrito de un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se listan los productos en el carrito", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Carrito> verCarrito(@PathVariable String usuarioId) {
        Carrito carrito = carritoService.obtenerCarritoPorUsuario(usuarioId);
        return ResponseEntity.ok(carrito);
    }

    @PostMapping("/agregar")
    @Operation(summary = "Agregar", description = "Agrega un producto a un carrito por el id asociado ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto agregado exitosamente", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<Carrito> agregarAlCarrito(@RequestBody com.example.antuco.carrito.dto.AgregarItemRequest request) {
        Carrito carritoActualizado = carritoService.agregarItem(request);
        return ResponseEntity.ok(carritoActualizado);
    }

    @PostMapping("/agregar-simple")
    @Operation(summary = "Agregar simple", description = "Agrega un producto, pero con syntax mas simple")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto agregado con exito", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<String> agregarSimple(@RequestBody Map<String, Object> payload) {
        String usuarioId = (String) payload.get("usuarioId");
        Long productoId = Long.valueOf(payload.get("productoId").toString());
        Integer cantidad = Integer.valueOf(payload.get("cantidad").toString());

        carritoService.agregarItemSimple(usuarioId, productoId, cantidad);
        return ResponseEntity.ok("Agregado desde catálogo con éxito");
    }

    @DeleteMapping("/{carritoId}/item/{itemId}")
    @Operation(summary = "Borrar producto", description = "Borra un producto del carrito acorde al id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto borrado", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<Void> eliminarItem(@PathVariable Long carritoId, @PathVariable Long itemId) {
        carritoService.eliminarItem(carritoId, itemId);
        return ResponseEntity.noContent().build();
    }
}
