package com.example.antuco.usuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.antuco.usuarios.dto.LoginRequest;
import com.example.antuco.usuarios.dto.RegistroRequest;
import com.example.antuco.usuarios.model.Usuario;
import com.example.antuco.usuarios.service.UsuarioService;

@RestController
@RequestMapping("/api/V1/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@RequestBody RegistroRequest request) {
        return ResponseEntity.ok(usuarioService.registrar(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Usuario usuario = usuarioService.login(request);
            // En un proyecto real, aquí devolverías un TOKEN (JWT)
            // Por ahora devolvemos el usuario para confirmar que funciona
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}