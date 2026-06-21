package com.grupodos.antuco.Catalogo.V1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @NotBlank(message = "El ID de la categoría no puede estar vacío")
    @JsonProperty("Id")
    private String id;


    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    @Column(name = "nombre_categoria", nullable = false)
    private String nombreCategoria;

    public Categoria(String id, String nombreCategoria) {
        this.id = id;
        this.nombreCategoria = nombreCategoria;
    }


}
