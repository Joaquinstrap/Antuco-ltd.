package com.example.antuco.inventario.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.antuco.inventario.service.ServiceInventario;

import jakarta.validation.Valid;

import com.example.antuco.inventario.model.DTOinventory;
import com.example.antuco.inventario.model.Inventariomodel;




@RestController
@RequestMapping("/api/V1/inventario")
public class InventoryController {


    @Autowired
    private ServiceInventario serviceInventario;


    @GetMapping("/producto/{productoId}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Long productoId){
        try{
            Inventariomodel producto = serviceInventario.obtenerPorProductoId(productoId);
            return ResponseEntity.ok(producto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity<?> crearRegistro(@Valid @RequestBody DTOinventory dto){
        try{

            Inventariomodel nuevoregistro = serviceInventario.CreacionNuevoRegistro(
            dto.getProductoId(), 
            dto.getCantidad(), 
            dto.getTipoProducto()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoregistro);
            
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }

    }

    @PutMapping("/producto/{productoId}/incrementar")
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
