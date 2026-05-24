package com.example.antuco.eventos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.antuco.eventos.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Consulta personalizada para sumar cuántas entradas se han vendido de un evento
    @Query("SELECT SUM(r.cantidad) FROM Reserva r WHERE r.eventoId = :eventoId")
    Optional<Integer> totalEntradasVendidas(@Param("eventoId") Long eventoId);
}