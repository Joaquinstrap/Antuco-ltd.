package com.example.antuco.inventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Data
@Table(name = "inventarios")
public class Inventariomodel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la asociacion del stock al usuario", example = "001")
    private Long id;


    @Column(name = "producto_id", nullable = false, unique = true)
    @Schema(description = "ID del producto original", example = "001")
    private Long productoId;


    @Column(nullable = false)
    @Schema(description = "El stock disponible", example = "523")
    private Integer cantidad;


    @Column(name = "tipo_producto", nullable = false)
    @Schema(description = "La categoria del producto, musica o ropa.", example = "02")
    private String tipoProducto;


    public Inventariomodel(Long productoId, Integer cantidad, String tipoProducto){
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.tipoProducto = tipoProducto;
    }


}
