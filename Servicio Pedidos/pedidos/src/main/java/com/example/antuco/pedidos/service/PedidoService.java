package com.example.antuco.pedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.antuco.pedidos.client.InventarioClient;
import com.example.antuco.pedidos.client.PagosClient;
import com.example.antuco.pedidos.dto.CrearPedidoRequest;
import com.example.antuco.pedidos.dto.ItemPedidoDTO;
import com.example.antuco.pedidos.model.DetallePedido;
import com.example.antuco.pedidos.model.Pedido;
import com.example.antuco.pedidos.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private InventarioClient inventarioClient; // el del juaco

    @Autowired
    private PagosClient pagosClient; // El otro servicio

    @Transactional
    public Pedido crearPedido(CrearPedidoRequest request) {
        // 1. Validar
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("El pedido no tiene items");
        }

        // 2. Calcular Total y Verificar Stock (llamando a Inventario)
        Double total = 0.0;
        for (ItemPedidoDTO item : request.getItems()) {
            total += (item.getPrecioUnitario() * item.getCantidad());
            // Aquí conectamos con el servicio del juaco
            inventarioClient.restarStock(item.getProductoId(), item.getCantidad());
        }

        // 3. Procesar Pago (llamando a Pagos)
        // Nota: Si esto falla, la transacción se revertirá, pero el stock ya se restó en Inventario.
        try {
            pagosClient.procesarPago(total, "TARJETA");
        } catch (Exception e) {
            throw new RuntimeException("Fallo el pago: " + e.getMessage());
        }

        // 4. Guardar Pedido en BD
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setUsuarioUsername(request.getUsuarioUsername());
        nuevoPedido.setTotal(total);
        nuevoPedido.setEstado("PAGADO");

        for (ItemPedidoDTO itemDto : request.getItems()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setProductoId(itemDto.getProductoId());
            detalle.setNombreProducto(itemDto.getNombreProducto());
            detalle.setCantidad(itemDto.getCantidad());
            detalle.setPrecioUnitario(itemDto.getPrecioUnitario());
            nuevoPedido.agregarDetalle(detalle);
        }

        return pedidoRepository.save(nuevoPedido);
    }
}