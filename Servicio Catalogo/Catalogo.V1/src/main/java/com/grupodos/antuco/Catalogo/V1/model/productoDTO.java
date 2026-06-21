package com.grupodos.antuco.Catalogo.V1.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class productoDTO {
    // Campos comunes
    private String nombre_producto;
    private double precio;
    private String categoria;
    private String dtype; // Aquí el usuario pondrá "Ropa" o "Musicafisica"

    // Campos específicos de Ropa
    private String materialcreacion;
    private String talla;

    // Campos específicos de Música Física
    
    private String album;
    private int numerocanciones;
    private String tipoformato;

    // Para tus atributos dinámicos (opcional, si quieres mandar llave-valor directo)
    private Map<String, String> atributosExtra;
}
