package com.example.antuco.pedidos.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PagosClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String PAGOS_URL = "http://localhost:8084/api/V1/pagos";

    public boolean procesarPago(Double monto, String metodoPago) {
        String url = PAGOS_URL + "/procesar"; 
        
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, Map.of("monto", monto, "metodo", metodoPago), String.class);
            
            // Asumimos que si responde con 200 OK, el pago fue exitoso
            return response.getStatusCode() == HttpStatus.OK;
            
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el pago: " + e.getMessage());
        }
    }
}