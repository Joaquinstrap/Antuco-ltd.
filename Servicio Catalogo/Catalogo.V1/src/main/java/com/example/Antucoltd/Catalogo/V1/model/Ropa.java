package com.example.Antucoltd.Catalogo.V1.model;

import jakarta.persistence.*;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
//Colocando lo de arriba tomara en cuenta las validaciones de la super clase asi podra validar si hay objetos iguales 
@Data
@Entity
public class Ropa extends Productosdelcatalogo {

    @Transient
    private String talla;

    @Transient
    private String materialcreacion;


    public Ropa(String nombre_producto, Categoria categoria, Double precio, String talla, String materialcreacion){
        super(nombre_producto, categoria, precio);
        this.talla = talla;
        this.materialcreacion = materialcreacion;
        
        this.getAtributos().add(new ProductoAtributo(this, "talla", talla));
        this.getAtributos().add(new ProductoAtributo(this, "materialcreacion", materialcreacion));


    }    


    


}
