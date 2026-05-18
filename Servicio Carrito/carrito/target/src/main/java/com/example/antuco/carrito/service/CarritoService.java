package com.example.antuco.carrito.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.antuco.carrito.dto.AgregarItemRequest;
import com.example.antuco.carrito.model.Carrito;
import com.example.antuco.carrito.model.ItemCarrito; // <--- ESTA ERA LA LÍNEA QUE FALTABA
import com.example.antuco.carrito.repository.CarritoRepository;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    // Obtener o crear carrito por usuario
    @Transactional(readOnly = true)
    public Carrito obtenerCarritoPorUsuario(String usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setUsuarioId(usuarioId);
                    return carritoRepository.save(nuevoCarrito);
                });
    }

    // Agregar item al carrito
    @Transactional
    public Carrito agregarItem(AgregarItemRequest request) {
        Carrito carrito = obtenerCarritoPorUsuario(request.getUsuarioId());

        // Verificar si el producto ya existe en el carrito para actualizar cantidad
        Optional<ItemCarrito> itemExistente = carrito.getItems().stream()
                .filter(item -> item.getProductoId().equals(request.getProductoId()))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + request.getCantidad());
        } else {
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setProductoId(request.getProductoId());
            nuevoItem.setNombreProducto(request.getNombreProducto());
            nuevoItem.setPrecioUnitario(request.getPrecioUnitario());
            nuevoItem.setImagenUrl(request.getImagenUrl());
            nuevoItem.setCantidad(request.getCantidad());
            
            carrito.agregarItem(nuevoItem);
        }

        return carritoRepository.save(carrito);
    }

    // Eliminar item del carrito
    @Transactional
    public void eliminarItem(Long carritoId, Long itemId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        
        carrito.getItems().removeIf(item -> item.getId().equals(itemId));
        carritoRepository.save(carrito);
    }
    
    // Calcular total del carrito (helper opcional)
    public BigDecimal calcularTotal(Carrito carrito) {
        return carrito.getItems().stream()
                .map(item -> item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}