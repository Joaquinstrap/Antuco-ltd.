package com.example.antuco.catalogo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @NotBlank(message = "El ID de la categoría no puede estar vacío")
    private String id;


    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    @Column(name = "nombre_categoria", nullable = false)
    private String nombreCategoria;

    public Categoria(String id, String nombreCategoria) {
        this.id = id;
        this.nombreCategoria = nombreCategoria;
    }


}
