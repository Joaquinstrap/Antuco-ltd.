package com.example.antuco.pedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.antuco.pedidos.dto.CrearPedidoRequest;
import com.example.antuco.pedidos.model.Pedido;
import com.example.antuco.pedidos.service.PedidoService;

@RestController
@RequestMapping("/api/V1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(@RequestBody CrearPedidoRequest request) {
        try {
            Pedido pedido = pedidoService.crearPedido(request);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}