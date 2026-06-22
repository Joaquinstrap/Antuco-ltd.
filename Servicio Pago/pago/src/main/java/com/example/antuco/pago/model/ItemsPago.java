package com.example.antuco.pago.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "items_pago")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ItemsPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pago_id", nullable = false)
    private Pago pago;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "precio_unidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnidad;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    // Constructor para crear el item por si solo
    public ItemsPago(Long productoId, String nombreProducto, BigDecimal precioUnidad) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.precioUnidad = precioUnidad;
    }

}
