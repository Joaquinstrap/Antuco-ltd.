package com.example.antuco.eventos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.antuco.eventos.dto.CrearEventoRequest;
import com.example.antuco.eventos.dto.ReservarRequest;
import com.example.antuco.eventos.model.Evento;
import com.example.antuco.eventos.model.Reserva;
import com.example.antuco.eventos.service.EventoService;

@RestController
@RequestMapping("/api/V1/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // Crear Evento
    @PostMapping
    public ResponseEntity<Evento> crearEvento(@RequestBody CrearEventoRequest request) {
        return ResponseEntity.ok(eventoService.crearEvento(request));
    }

    // Listar Eventos
    @GetMapping
    public ResponseEntity<List<Evento>> listarEventos() {
        return ResponseEntity.ok(eventoService.listarEventos());
    }

    // Reservar Entrada
    @PostMapping("/reservar")
    public ResponseEntity<?> reservar(@RequestBody Map<String, Object> payload) {
        try {
            ReservarRequest request = new ReservarRequest();
            request.setEventoId(Long.valueOf(payload.get("eventoId").toString()));
            request.setCantidad(Integer.valueOf(payload.get("cantidad").toString()));
            
            String usuario = (String) payload.get("usuarioUsername"); // Simulado

            Reserva reserva = eventoService.reservarEntrada(request, usuario);
            return ResponseEntity.ok("Reserva exitosa para el evento ID: " + reserva.getEventoId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Ver reservas
    @GetMapping("/reservas")
    public ResponseEntity<List<Reserva>> verReservas() {
        return ResponseEntity.ok(eventoService.verMisReservas("user"));
    }
}