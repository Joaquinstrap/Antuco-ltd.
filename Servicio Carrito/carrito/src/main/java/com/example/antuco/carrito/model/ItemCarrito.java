package com.example.antuco.carrito.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la transaccion del producto al carrito", example = "001")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id", nullable = false)
    @JsonIgnore
    private Carrito carrito;

    // Datos que vienen del servicio de catálogo (Snapshot)
    @Schema(description = "ID original del producto", example = "001")
    private Long productoId; 

    @Schema(description = "Nombre del producto", example = "Polera de Banda")
    private String nombreProducto;

    @Schema(description = "Precio unitario del producto", example = "$19.990")
    private BigDecimal precioUnitario;

    @Schema(description = "URL de la imagen del producto", example = "https://www.tienda.com/assets/album1/cover.jpg")
    private String imagenUrl;

    @Schema(description = "Cantidad de unidades pedidas del producto", example = "2")
    private Integer cantidad;
}
