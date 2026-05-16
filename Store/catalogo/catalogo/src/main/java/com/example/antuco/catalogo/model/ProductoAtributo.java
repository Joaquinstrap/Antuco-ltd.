package com.example.antuco.catalogo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "producto_atributos")
@Data
@NoArgsConstructor
public class ProductoAtributo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Productosdelcatalogo producto;

    @NotBlank(message = "El nombre del atributo no puede estar vacío")
    @Column(name = "nombre_atributo")
    private String nombreAtributo;
    
    @NotBlank(message = "El valor del atributo no puede estar vacío")
    @Column(name = "valor_atributo")
    private String valorAtributo;

    public ProductoAtributo(Productosdelcatalogo producto, String nombreAtributo, String valorAtributo) {
        this.producto = producto;
        this.nombreAtributo = nombreAtributo;
        this.valorAtributo = valorAtributo;
    }






}
