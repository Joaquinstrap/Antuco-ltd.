package com.example.antuco.comentarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.antuco.comentarios.dto.CrearComentarioRequest;
import com.example.antuco.comentarios.model.Comentario;
import com.example.antuco.comentarios.repository.ComentarioRepository;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    // Crear un nuevo comentario
    public Comentario crearComentario(CrearComentarioRequest request, String usuarioUsername) {
        Comentario nuevoComentario = new Comentario();
        nuevoComentario.setProductoId(request.getProductoId());
        nuevoComentario.setUsuarioUsername(usuarioUsername);
        nuevoComentario.setTexto(request.getTexto());
        nuevoComentario.setCalificacion(request.getCalificacion());

        return comentarioRepository.save(nuevoComentario);
    }

    // Obtener comentarios de un producto
    public List<Comentario> obtenerComentariosPorProducto(Long productoId) {
        return comentarioRepository.findByProductoIdOrderByFechaCreacionDesc(productoId);
    }
    
    // Eliminar comentario (opcional)
    public void eliminarComentario(Long id) {
        comentarioRepository.deleteById(id);
    }
}