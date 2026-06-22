package com.example.antuco.pago.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pago")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long id;

    @Column(name = "nom_usuario", nullable = false)
    private String nomUsuario;

    @Column(name = "id_carro", nullable = false)
    private Long idCarro;

    @Column(name = "fecha_pago", updatable = false)
    private LocalDateTime fechaPago;

    @Column(name = "status_pago", nullable = false)
    private String statusPago = "Procesando";

    @OneToMany(mappedBy = "pago", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemsPago> items = new ArrayList<>();

    // Helper para agregar items
    public void agregarItem(ItemsPago item) {
        items.add(item);
        item.setPago(this);
    }

    // Constructor para la creacion
    public Pago(String nomUsuario, Long idCarro) {
        this.nomUsuario = nomUsuario;
        this.idCarro = idCarro;
        this.fechaPago = LocalDateTime.now();
        this.statusPago = "PROCESANDO";
    }

}
