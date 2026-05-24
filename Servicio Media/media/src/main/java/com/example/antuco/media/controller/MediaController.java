package com.example.antuco.media.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.antuco.media.model.Cancion;
import com.example.antuco.media.service.MediaService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/V1/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    // 1. Subir Canción
    @PostMapping("/subir")
    public ResponseEntity<?> subirCancion(
            @RequestParam("titulo") String titulo,
            @RequestParam("idAlbum") String idAlbum,
            @RequestParam("file") MultipartFile file) {
        try {
            Cancion cancion = mediaService.subirCancion(titulo, idAlbum, file);
            return ResponseEntity.ok("Canción subida con ID: " + cancion.getId());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al subir archivo: " + e.getMessage());
        }
    }

    // 2. Listar canciones de un álbum
    @GetMapping("/album/{idAlbum}")
    public ResponseEntity<List<Cancion>> obtenerPlaylist(@PathVariable String idAlbum) {
        return ResponseEntity.ok(mediaService.listarPorAlbum(idAlbum));
    }

    // 3. REPRODUCIR CANCIÓN (Corregido)
    @GetMapping("/reproducir/{id}")
    public void reproducirCancion(@PathVariable Long id, HttpServletResponse response) throws IOException {
        // Buscar canción en BD
        Cancion cancion = mediaService.listarPorAlbum("any").stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Canción no encontrada"));

        // CORREGIDO AQUÍ: Usamos 'Paths' correctamente
        Path filePath = Paths.get(uploadDir).resolve(cancion.getNombreArchivo()).toAbsolutePath();
        File archivo = filePath.toFile();

        if (!archivo.exists()) {
            throw new RuntimeException("El archivo de audio no existe en el servidor");
        }

        // Configurar respuesta para que el navegador sepa que es audio
        response.setContentType("audio/mpeg"); 
        response.setHeader("Content-Disposition", "inline; filename=\"" + archivo.getName() + "\"");
        response.setContentLength((int) archivo.length());

        // Enviar el archivo al navegador
        Files.copy(filePath, response.getOutputStream());
        response.flushBuffer();
    }
}