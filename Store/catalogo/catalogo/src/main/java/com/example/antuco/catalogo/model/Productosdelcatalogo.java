package com.example.antuco.catalogo.model;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//Esta sirve para decirle a la base de datos que se va a usar una sola tabla para todas las clases
@Table(name = "productosdelcatalogo")
public abstract class Productosdelcatalogo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "El nombre del producto debe ser correcto")
    @Column(nullable = false)
    private String nombre_producto;


    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @NotNull(message = "El precio debe ser correcto")
    @Column(nullable = false)
    private Double precio;


    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<ProductoAtributo> atributos = new ArrayList<>();


    public Productosdelcatalogo(String nombre_producto, Categoria categoria, Double precio){
        this.nombre_producto = nombre_producto;
        this.categoria = categoria;
        this.precio = precio;
    }

    


}
