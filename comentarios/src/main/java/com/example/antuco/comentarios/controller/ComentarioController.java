package com.example.antuco.comentarios.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.antuco.comentarios.dto.CrearComentarioRequest;
import com.example.antuco.comentarios.model.Comentario;
import com.example.antuco.comentarios.service.ComentarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/V1/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    // Endpoint para crear comentario
    // El username lo pasamos en el body por simplicidad académica
    @PostMapping
    public ResponseEntity<Comentario> crearComentario(@Valid @RequestBody CrearComentarioRequest request) {
        // Simulamos obtener el usuario. En producción esto vendría del Token JWT
        String usuarioSimulado = "fan_duoc"; 
        
        Comentario comentario = comentarioService.crearComentario(request, usuarioSimulado);
        return ResponseEntity.ok(comentario);
    }
    
    // Endpoint para crear comentario pasando el usuario en el JSON (opcional)
    @PostMapping("/con-usuario")
    public ResponseEntity<Comentario> crearConUsuario(@RequestBody Map<String, Object> payload) {
        CrearComentarioRequest request = new CrearComentarioRequest();
        request.setProductoId(Long.valueOf(payload.get("productoId").toString()));
        request.setTexto((String) payload.get("texto"));
        request.setCalificacion(Integer.valueOf(payload.get("calificacion").toString()));
        
        String usuario = (String) payload.get("usuarioUsername");
        
        Comentario comentario = comentarioService.crearComentario(request, usuario);
        return ResponseEntity.ok(comentario);
    }

    // Obtener comentarios de un producto específico
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable Long productoId) {
        return ResponseEntity.ok(comentarioService.obtenerComentariosPorProducto(productoId));
    }

    // Eliminar comentario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }
}