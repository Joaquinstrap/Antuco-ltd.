package com.example.antuco.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.antuco.eventos.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}