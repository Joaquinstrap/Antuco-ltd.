package com.example.antuco.inventario.Model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor
@Data
@Table(name = "inventarios")
public class Inventariomodel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "producto_id", nullable = false, unique = true)
    private Long productoId;


    @Column(nullable = false)
    private Integer cantidad;


    @Column(name = "tipo_producto", nullable = false)
    private String tipoProducto;


    public Inventariomodel(Long productoId, Integer cantidad, String tipoProducto){
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.tipoProducto = tipoProducto;
    }


}
