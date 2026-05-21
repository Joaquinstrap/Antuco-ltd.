package com.antuco.pago.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antuco.pago.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    // Para buscar el historial de un usuario
    List<Pago> findByIdUsuario(String usuarioId);
}
