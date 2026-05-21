package com.antuco.pago.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.antuco.pago.dto.CarritoResponseDTO;
import com.antuco.pago.dto.UsuarioResponseDTO;
import com.antuco.pago.model.Pago;
import com.antuco.pago.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private RestTemplate restTemplate;

    // URLs de los otros microservicios (Lo ideal en el futuro es usar API Gateway/Eureka)
    private final String URL_USUARIOS = "http://localhost:8082/api/V1/usuarios"; // Asumiendo que tienes este endpoint
    private final String URL_CARRITO = "http://localhost:8081/api/V1/carrito";

    @Transactional
    public Pago procesarPago(String usuarioId) {
        
        // 1. Consultar microservicio de Usuarios
        // OJO: En tu código de usuarios no tenías endpoint para buscar por username. 
        // Asumamos que creas uno: GET /api/V1/usuarios/{username}
        UsuarioResponseDTO usuario = restTemplate.getForObject(
            URL_USUARIOS + "/" + usuarioId, UsuarioResponseDTO.class);
        
        if (usuario == null) {
            throw new RuntimeException("El usuario no existe");
        }

        // 2. Consultar microservicio de Carrito
        CarritoResponseDTO carrito = restTemplate.getForObject(
            URL_CARRITO + "/" + usuarioId, CarritoResponseDTO.class);

        if (carrito == null || carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío o no existe");
        }

        // 3. Construir el objeto Pago
        Pago pago = new Pago();
        pago.setIdUsuario(usuarioId);
        pago.setNomUsuario(usuario.getUsername()); // Guardamos el snapshot del nombre
        pago.setIdCarro(carrito.getId());
        pago.setStatusPago("COMPLETADO");

        // 4. Mover los items del carrito al pago
        for (CarritoResponseDTO.ItemCarritoResponseDTO itemCarrito : carrito.getItems()) {
            ItemPago itemPago = new ItemPago();
            itemPago.setProductoId(itemCarrito.getProductoId());
            itemPago.setNombreProducto(itemCarrito.getNombreProducto());
            itemPago.setPrecioUnidad(itemCarrito.getPrecioUnitario());
            itemPago.setCantidad(itemCarrito.getCantidad());
            
            pago.agregarItem(itemPago); // Usamos el helper para linkear la FK
        }

        // 5. Guardar en la base de datos de Pagos
        Pago pagoGuardado = pagoRepository.save(pago);

        // 6. FUTURO: Aquí mandarías a llamar al microservicio de Inventario para descontar stock
        // y luego llamarías al Carrito para vaciarlo/borrarlo.

        return pagoGuardado;
    }
}