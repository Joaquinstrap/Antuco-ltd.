package com.example.antuco.pedidos.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventarioClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String INVENTARIO_URL = "http://localhost:8087/api/V1/inventario";

    public void restarStock(Long productoId, Integer cantidad) {
        String url = INVENTARIO_URL + "/producto/" + productoId + "/decrementar?cantidad=" + cantidad;
        
        try {
            // Capturamos la respuesta para que el IDE no se queje
            ResponseEntity<Void> response = restTemplate.exchange(
                url, 
                org.springframework.http.HttpMethod.PUT, 
                null, 
                Void.class
            );

            // Verificamos que el código de respuesta sea exitoso (200, 201, etc)
            if (response.getStatusCode() != HttpStatus.OK && response.getStatusCode() != HttpStatus.ACCEPTED) {
                 throw new RuntimeException("Error al restar stock. Código: " + response.getStatusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error de conexión con Inventario: " + e.getMessage());
        }
    }
}