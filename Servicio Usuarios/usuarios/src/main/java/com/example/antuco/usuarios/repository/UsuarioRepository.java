package com.example.antuco.usuarios.repository;

import org.springframework.stereotype.Repository;
import com.example.antuco.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
};
