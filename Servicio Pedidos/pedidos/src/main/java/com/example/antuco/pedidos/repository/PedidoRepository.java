package com.example.antuco.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.antuco.pedidos.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}