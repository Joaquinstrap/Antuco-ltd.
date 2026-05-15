package com.antuco.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antuco.catalogo.model.Merch;

public interface MerchRepo extends JpaRepository<Merch, Long>{
}
