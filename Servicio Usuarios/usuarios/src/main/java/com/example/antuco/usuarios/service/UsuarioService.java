package com.example.antuco.usuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.antuco.usuarios.dto.RegistroRequest;
import com.example.antuco.usuarios.model.Usuario;
import com.example.antuco.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registrar nuevo usuario
    public Usuario registrar(RegistroRequest request) {
        if (usuarioRepository.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(request.getUsername());
        nuevoUsuario.setRol(request.getRol() != null ? request.getRol() : "USER");

        return usuarioRepository.save(nuevoUsuario);
    }
}