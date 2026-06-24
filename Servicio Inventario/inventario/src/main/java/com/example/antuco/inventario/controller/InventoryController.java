package com.example.antuco.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.antuco.inventario.model.DTOinventory;
import com.example.antuco.inventario.model.Inventariomodel;
import com.example.antuco.inventario.service.ServiceInventario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/V1/inventario")
@Tag(name = "Inventario", description = "Manejo de stock de productos")
public class InventoryController {

    @Autowired
    private ServiceInventario serviceInventario;

    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Obtener Producto", description = "Muestra los datos de un producto especifico")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado", 
                     content = @Content(schema = @Schema(implementation = DTOinventory.class))),
        @ApiResponse(responseCode = "404", description = "No se encuentra el producto", 
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error inesperado")
    })
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Long productoId){
        try{
            Inventariomodel producto = serviceInventario.obtenerPorProductoId(productoId);
            return ResponseEntity.ok(producto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping
    @Operation(summary = "Crear registro", description = "Crea un registro de stock para un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro hecho correctamente", 
            content = @Content(schema = @Schema(implementation = DTOinventory.class))),
        @ApiResponse(responseCode = "400", description = "Se ha escrito mal un parametro al enviar.", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "No se encuentra el producto para agregar stock", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error inesperado", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> crearRegistro(@Valid @RequestBody DTOinventory dto){
        try {
            Inventariomodel nuevoregistro = serviceInventario.CreacionNuevoRegistro(
            dto.getProductoId(), 
            dto.getCantidad(), 
            dto.getTipoProducto()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoregistro);
            
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @PutMapping("/producto/{productoId}/incrementar")
    @Operation(summary = "Incrementar Stock", description = "Edita el stock de un producto, aumentandolo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock incrementado", 
            content = @Content(schema = @Schema(implementation = DTOinventory.class))),
        @ApiResponse(responseCode = "400", description = "Mal formato. Debe ser numero", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error inesperado", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> incrementarStock(@PathVariable Long productoId, @RequestParam Integer cantidad){
        try{
            Inventariomodel productoActualizado = serviceInventario.IncrementarStock(productoId, cantidad);
            return ResponseEntity.ok(productoActualizado);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @PutMapping("/producto/{productoId}/decrementar")
    @Operation(summary = "Reducir Stock", description = "Edita el stock en un producto, reduciendolo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock decrementado", 
            content = @Content(schema = @Schema(implementation = DTOinventory.class))),
        @ApiResponse(responseCode = "400", description = "Mal formato. Debe ser numero", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error inesperado", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> decrementarStock(@PathVariable Long productoId, @RequestParam Integer cantidad){
        try{
            Inventariomodel productoActualizado = serviceInventario.DecrementarStock(productoId, cantidad);
            return ResponseEntity.ok(productoActualizado);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @DeleteMapping("/producto/{productoId}")
    @Operation(summary = "Eliminar Registro", description = "Borra el registro de stock de un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro eliminado exitosamente", 
            content = @Content(schema = @Schema(implementation = DTOinventory.class))),
        @ApiResponse(responseCode = "400", description = "Se ha escrito mal un parametro al enviar.", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "No se encuentra el producto a borrar", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error inesperado", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> EliminarRegistro(@PathVariable Long productoId){
        try{
            serviceInventario.EliminarRegistro(productoId);
            return ResponseEntity.ok("Registro eliminado exitosamente");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

}
