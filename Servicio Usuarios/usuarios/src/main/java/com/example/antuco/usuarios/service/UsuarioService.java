package com.example.antuco.usuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.antuco.usuarios.dto.RegistroRequest;
import com.example.antuco.usuarios.model.Usuario;
import com.example.antuco.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    // Por agregar:
    // Listar usuarios
    // Banear gente

    // Registrar nuevo usuario
    public Usuario registrar(RegistroRequest request) {
        usuarioRepository.findByUsername(request.getUsername())
            .ifPresent(user -> { throw new RuntimeException("El usuario ya existe"); });

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(request.getUsername());
        nuevoUsuario.setRol(request.getRol() != null ? request.getRol() : "USER");

        nuevoUsuario.setClave(passwordEncoder.encode(request.getClave()));

        return usuarioRepository.save(nuevoUsuario);
    }

}
