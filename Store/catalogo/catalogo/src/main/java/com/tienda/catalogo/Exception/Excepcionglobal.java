package com.tienda.catalogo.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Excepcionglobal {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarErroresDeValidacion(IllegalArgumentException ex) {
        // Devuelve solo el texto del mensaje con un error 400
        return ResponseEntity.badRequest().body(ex.getMessage());
    }



}
