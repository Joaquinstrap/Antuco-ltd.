package com.example.antuco.pago.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.antuco.pago.dto.CarritoResponseDTO;
import com.example.antuco.pago.dto.CarritoResponseDTO.ItemCarritoResponseDTO;
import com.example.antuco.pago.model.ItemsPago;
import com.example.antuco.pago.model.Pago;
import com.example.antuco.pago.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String URL_CARRITO = "http://localhost:8081/api/V1/carrito";

    @Transactional
    public Pago procesarPago(Long idCarro, String nombreUsuario) {
        // 1. Obtener información del carrito del OTRO servicio
        // Nota: Esto asume que el otro servicio tiene un endpoint GET /api/carros/{id}
        CarritoResponseDTO carrito = restTemplate.getForObject(URL_CARRITO + idCarro, CarritoResponseDTO.class);

        if (carrito == null || carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío o no existe");
        }

        // 2. Crear la entidad Pago
        Pago nuevoPago = new Pago(nombreUsuario, idCarro);
        
        // 3. Convertir items del carrito externo a items de pago internos
        for (ItemCarritoResponseDTO itemExterno : carrito.getItems()) {
            // Convertimos Double a BigDecimal para precisión monetaria
            BigDecimal precio = itemExterno.getPrecioUnitario();
            
            ItemsPago itemsPago = new ItemsPago(
                itemExterno.getProductoId(),
                itemExterno.getNombreProducto(),
                precio
            );
            
            nuevoPago.agregarItem(itemsPago);
        }

        // 4. Simular proceso de pago (aquí podrías integrar con Stripe/PayPal)
        boolean pagoExitoso = realizarPagoConPasarela(nuevoPago);

        if (pagoExitoso) {
            nuevoPago.setStatusPago("COMPLETADO");
        } else {
            nuevoPago.setStatusPago("FALLIDO");
        }

        // 5. Guardar en base de datos (Cascada guardará los items automáticamente)
        return pagoRepository.save(nuevoPago);
    }
    private boolean realizarPagoConPasarela(Pago pago) {
    // Lógica simulada
    // Aquí sumarías los precios y llamarías a una API real
        return true; // Siempre true por ahora
    }
}