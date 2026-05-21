package com.example.antuco.usuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // Supuestamente hay que agregar esto para que funcione el pago?
    // Z.ai tiene demencia ojo vecinos
    @GetMapping("/{username}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }
    
}
