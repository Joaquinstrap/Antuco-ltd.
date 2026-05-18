package com.example.antuco.autenticacion.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.antuco.autenticacion.service.AutenticacionService;

@RestController
@RequestMapping("/api/V1/autenticacion")
public class AutenticacionController {

    @Autowired
    private AutenticacionService autenticacionService;

    // Endpoint de Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        try {
            String token = autenticacionService.login(username, password);
            return ResponseEntity.ok(Map.of("token", token, "mensaje", "Login exitoso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    
    // Endpoint para registrar (Pruebas)
    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody Map<String, String> request) {
        autenticacionService.registrar(request.get("username"), request.get("password"));
        return ResponseEntity.ok("Credenciales guardadas en servicio de Autenticación");
    }
}