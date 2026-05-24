package com.example.Antucoltd.Catalogo.V1.model;



import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//Esta sirve para decirle a la base de datos que se va a usar una sola tabla para todas las clases
@Table(name = "productosdelcatalogo")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "dtype")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Ropa.class, name = "Ropa")
    // Aquí puedes agregar más comas y clases si tienes otras, ej:
    // , @JsonSubTypes.Type(value = Musicafisica.class, name = "Musicafisica")
})
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


    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ProductoAtributo> atributos = new ArrayList<>();


    public Productosdelcatalogo(String nombre_producto, Categoria categoria, Double precio){
        this.nombre_producto = nombre_producto;
        this.categoria = categoria;
        this.precio = precio;
    }

    // Método ayudante para entrelazar el producto con sus atributos antes de guardar
    public void enlazarAtributos() {
        if (this.atributos != null) {
            for (ProductoAtributo atributo : this.atributos) {
                atributo.setProducto(this); // Le asigna este producto (padre) a cada atributo hijo
            }
        }
    }


}
