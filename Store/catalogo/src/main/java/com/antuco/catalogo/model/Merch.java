package com.antuco.catalogo.model;

// import java.time.LocalDateTime;
// import org.hibernate.annotations.CreationTimestamp;
// import com.fasterxml.jackson.annotation.JsonFormat;
// import jakarta.validation.constraints.PastOrPresent;
// Cosos de la fecha de abajo

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "merch")
@Entity // es necesario noma poner esto en los model
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nombre vacio o mal escrito")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "Es necesario al menos una descripcion")
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotBlank(message = "No se puede tener merch sin imagen")
    @Column(name = "merch_url", nullable = false)
    private String merchUrl;

/* Copiado del catalogo antiguo, no se si habra que adaptarlo
    @NotNull(message = "La fecha de creacion es obligatoria")
    @PastOrPresent(message = "La fecha de creacion debe ser correcta")
    // la fecha tiene que ser entre el dia
    // de hoy o ayer, pero tiene que ser el tiempo en el que se requiera
    // no una fecha adelantada ni falsa
    @Column(name = "createdAt", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdAt;
    
*/
}
