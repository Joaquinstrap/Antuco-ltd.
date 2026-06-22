package com.example.antuco.pago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.antuco.pago.model.Pago;
import com.example.antuco.pago.service.PagoService;

@RestController
@RequestMapping("/api/V1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/procesar")
    public ResponseEntity<Pago> crearPago(
            @RequestParam Long idCarro,
            @RequestParam String usuario) {
        
        Pago pago = pagoService.procesarPago(idCarro, usuario);
        return ResponseEntity.ok(pago);
    }
}