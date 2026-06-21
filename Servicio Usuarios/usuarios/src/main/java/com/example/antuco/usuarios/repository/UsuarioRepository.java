package com.example.antuco.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.antuco.usuarios.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Buscar usuario por el nombre (username/email)
    Optional<Usuario> findByUsername(String username);
}
