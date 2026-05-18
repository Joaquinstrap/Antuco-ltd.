package com.example.antuco.usuarios.service;

import com.example.antuco.usuarios.dto.LoginRequest;
import com.example.antuco.usuarios.dto.RegistroRequest;
import com.example.antuco.usuarios.model.Usuario;
import com.example.antuco.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Lo inyectaremos en el main

    // Registrar nuevo usuario
    public Usuario registrar(RegistroRequest request) {
        if (usuarioRepository.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(request.getUsername());
        // Encriptamos la contraseña antes de guardar
        nuevoUsuario.setPassword(passwordEncoder.encode(request.getPassword()));
        nuevoUsuario.setRol(request.getRol() != null ? request.getRol() : "USER");

        return usuarioRepository.save(nuevoUsuario);
    }

    // Login (Devuelve el usuario si las credenciales son correctas)
    public Usuario login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername());
        
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Verifica si la contraseña enviada coincide con la encriptada en BD
        if (passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return usuario; // Login exitoso
        } else {
            throw new RuntimeException("Contraseña incorrecta");
        }
    }
}