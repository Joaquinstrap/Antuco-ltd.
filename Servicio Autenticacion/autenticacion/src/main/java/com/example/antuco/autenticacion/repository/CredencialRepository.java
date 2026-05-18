package com.example.antuco.autenticacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.antuco.autenticacion.model.Credencial;

public interface CredencialRepository extends JpaRepository<Credencial, Long> {
    Credencial findByUsername(String username);
}