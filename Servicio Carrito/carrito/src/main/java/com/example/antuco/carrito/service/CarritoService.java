package com.example.antuco.carrito.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.antuco.carrito.dto.AgregarItemRequest;
import com.example.antuco.carrito.dto.ProductoCatalogoDTO;
import com.example.antuco.carrito.model.Carrito;
import com.example.antuco.carrito.model.ItemCarrito;
import com.example.antuco.carrito.repository.CarritoRepository;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String URL_CATALOGO = "http://localhost:8080/api/V1/productos/";

    // ==========================================
    //  MÉTODOS PRINCIPALES
    // ==========================================

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

    // Agregar item al carrito (Recibe todos los datos en el Request)
    @Transactional
    public Carrito agregarItem(AgregarItemRequest request) {
        Carrito carrito = obtenerCarritoPorUsuario(request.getUsuarioId());

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

    // Agregar item consultando al Catálogo (Solo ID y Cantidad)
    @Transactional
    public void agregarItemSimple(String usuarioId, Long productoId, Integer cantidad) {
        // 1. Pedir datos al Catálogo
        ProductoCatalogoDTO producto = restTemplate.getForObject(URL_CATALOGO + productoId, ProductoCatalogoDTO.class);

        if (producto == null) {
            throw new RuntimeException("Producto no encontrado en el catálogo");
        }

        // 2. Usar la lógica que ya teníamos para guardar
        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);

        Optional<ItemCarrito> existente = carrito.getItems().stream()
                .filter(i -> i.getProductoId().equals(productoId))
                .findFirst();

        if (existente.isPresent()) {
            existente.get().setCantidad(existente.get().getCantidad() + cantidad);
        } else {
            ItemCarrito nuevo = new ItemCarrito();
            nuevo.setProductoId(producto.getId());
            nuevo.setNombreProducto(producto.getNombre());
            nuevo.setPrecioUnitario(BigDecimal.valueOf(producto.getPrecio()));
            nuevo.setCantidad(cantidad);
            carrito.agregarItem(nuevo);
        }

        carritoRepository.save(carrito);
    }

    // Eliminar item del carrito
    @Transactional
    public void eliminarItem(Long carritoId, Long itemId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        
        carrito.getItems().removeIf(item -> item.getId().equals(itemId));
        carritoRepository.save(carrito);
    }
    
    // Calcular total del carrito
    public BigDecimal calcularTotal(Carrito carrito) {
        return carrito.getItems().stream()
                .map(item -> item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}