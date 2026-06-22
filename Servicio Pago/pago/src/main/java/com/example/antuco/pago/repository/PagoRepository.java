package com.example.antuco.pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.antuco.pago.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}
