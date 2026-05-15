package com.antuco.catalogo.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "producto")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Para asociar el producto a un album acorde a la FK
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    // Lo mismo pero para merch
    @ManyToOne
    @JoinColumn(name = "merch_id")
    private Merch merch;

    private String sku;

    private BigDecimal precio; // BigDecimal es bueno para la plata

    private Integer stock;

    // Aqui van las specs que dice en el V1 sql. Se guardan como texto por ahora
    @Column(columnDefinition = "json")
    private String especificacion;

/* Para imprimir el nombre del producto para carrito. sin implementar todavia
    public String getDisplayName() {
        if (album != null) return album.getArtist() + " - " + album.getTitle();
        if (merchandise != null) return merchandise.getName();
        return "Unknown Item";
    }
*/
}
