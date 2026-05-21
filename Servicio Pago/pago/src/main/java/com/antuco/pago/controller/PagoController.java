package com.antuco.pago.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antuco.pago.model.Pago;
import com.antuco.pago.service.PagoService;

@RestController
@RequestMapping("/api/V1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    // Procesar un nuevo pago
    @PostMapping("/{usuarioId}")
    public ResponseEntity<Pago> pagar(@PathVariable String usuarioId) {
        Pago pago = pagoService.procesarPago(usuarioId);
        return ResponseEntity.ok(pago);
    }

    // Ver historial de compras de un usuario
    @GetMapping("/historial/{usuarioId}")
    public ResponseEntity<List<Pago>> historial(@PathVariable String usuarioId) {
        return ResponseEntity.ok(pagoService.buscarHistorial(usuarioId));
    }
}