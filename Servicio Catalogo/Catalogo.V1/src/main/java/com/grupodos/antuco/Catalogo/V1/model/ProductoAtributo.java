package com.grupodos.antuco.Catalogo.V1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "producto_atributos")
@Getter
@Setter
@NoArgsConstructor
public class ProductoAtributo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonBackReference
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
