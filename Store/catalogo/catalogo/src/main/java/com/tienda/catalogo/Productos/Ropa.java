package com.tienda.catalogo.Productos;



import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;


@Table(name = "Merch_banda")
@Entity//Recordar poner entity para que spring boot me detecte esta clase
public class Ropa {

    @NotNull(message = "Se necesita tener una Id correcta")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @NotBlank(message = "El nombre de la vestimenta tiene que ser correcta")
    @Column(name = "Nombre_prenda", nullable = false)
    private String Nombre_prenda;

    @NotBlank(message = "El tipo de la vestimenta debe ser correcto 'pantalon','camiseta','etc...'")
    @Column(name = "tipo_prenda", nullable = false)
    private String tipo_prenda;

    @NotNull(message = "La fecha de creacion es obligatoria")
    @PastOrPresent(message = "La echa de creacion debe ser correcta")
    //esta validacion indica que la fecha tiene que ser entre el dia
    //de hoy o ayer, pero tiene que ser el tiempo en el que se requiera
    //no una fecha adelantada ni falsa
    @Column(name = "createdAt", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdAt;

    





}
