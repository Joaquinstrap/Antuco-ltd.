package com.example.antuco.media.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.antuco.media.model.Cancion;
import com.example.antuco.media.repository.CancionRepository;

@Service
public class MediaService {

    @Autowired
    private CancionRepository cancionRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    // Subir canción
    public Cancion subirCancion(String titulo, String idAlbum, MultipartFile file) throws IOException {
        // 1. Validar que sea un archivo de audio
        if (file.isEmpty()) {
            throw new RuntimeException("El archivo está vacío");
        }

        // 2. Crear el directorio si no existe
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 3. Guardar el archivo en el disco duro
        String nombreUnico = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(nombreUnico);
        Files.copy(file.getInputStream(), filePath);

        // 4. Guardar los metadatos en la BD
        Cancion cancion = new Cancion();
        cancion.setTitulo(titulo);
        cancion.setIdAlbum(idAlbum);
        cancion.setNombreArchivo(nombreUnico);

        return cancionRepository.save(cancion);
    }

    // Obtener lista de reproducción de un álbum
    public List<Cancion> listarPorAlbum(String idAlbum) {
        return cancionRepository.findByIdAlbum(idAlbum);
    }
}