package com.example.Antucoltd.Catalogo.V1.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;




@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Musicafisica extends Productosdelcatalogo {


    @Transient
    private String tipoformato;


    @Transient
    private int numerocanciones;
    
    @Transient
    private String album;


    public Musicafisica(int numerocanciones, String album, String nombre_producto, Categoria categoria, Double precio, String tipoformato){
        super(nombre_producto, categoria, precio);
        this.numerocanciones = numerocanciones;
        this.album = album;
        this.tipoformato = tipoformato;

        this.getAtributos().add(new ProductoAtributo(this,"numerocanciones", String.valueOf(numerocanciones)));
        this.getAtributos().add(new ProductoAtributo(this, "album", album));
        this.getAtributos().add(new ProductoAtributo(this, "tipoformato", tipoformato));
    }

    


}
