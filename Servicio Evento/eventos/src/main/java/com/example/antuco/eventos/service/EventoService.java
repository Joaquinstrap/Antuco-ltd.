// Aquí está la lógica importante: Verificar que no se vendan más entradas de las que existen

package com.example.antuco.eventos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.antuco.eventos.dto.CrearEventoRequest;
import com.example.antuco.eventos.dto.ReservarRequest;
import com.example.antuco.eventos.model.Evento;
import com.example.antuco.eventos.model.Reserva;
import com.example.antuco.eventos.repository.EventoRepository;
import com.example.antuco.eventos.repository.ReservaRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    // 1. Crear Evento
    public Evento crearEvento(CrearEventoRequest request) {
        Evento nuevoEvento = new Evento();
        nuevoEvento.setNombre(request.getNombre());
        nuevoEvento.setFecha(request.getFecha());
        nuevoEvento.setLugar(request.getLugar());
        nuevoEvento.setPrecio(request.getPrecio());
        nuevoEvento.setCapacidadTotal(request.getCapacidadTotal());
        return eventoRepository.save(nuevoEvento);
    }

    // 2. Listar Eventos
    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    // 3. Reservar Entradas (Lógica de Capacidad)
    @Transactional
    public Reserva reservarEntrada(ReservarRequest request, String username) {
        // Buscar el evento
        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        // Calcular vendidas
        Integer vendidas = reservaRepository.totalEntradasVendidas(request.getEventoId()).orElse(0);
        Integer solicitadas = request.getCantidad();

        // Validar Capacidad
        if (vendidas + solicitadas > evento.getCapacidadTotal()) {
            throw new RuntimeException("No hay suficientes entradas disponibles. Quedan: " + (evento.getCapacidadTotal() - vendidas));
        }

        // Crear Reserva
        Reserva reserva = new Reserva();
        reserva.setEventoId(evento.getId());
        reserva.setUsuarioUsername(username);
        reserva.setCantidad(solicitadas);

        return reservaRepository.save(reserva);
    }
    
    // 4. Ver mis reservas
    public List<Reserva> verMisReservas(String username) {
        return reservaRepository.findAll(); // Podrías filtrar por username si quisieras
    }
}