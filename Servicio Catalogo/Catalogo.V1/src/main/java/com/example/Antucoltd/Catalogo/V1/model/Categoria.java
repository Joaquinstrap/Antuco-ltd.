package com.example.Antucoltd.Catalogo.V1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
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
    private String id;


    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    @Column(name = "nombre_categoria", nullable = false)
    private String nombreCategoria;

    public Categoria(String id, String nombreCategoria) {
        this.id = id;
        this.nombreCategoria = nombreCategoria;
    }


}
