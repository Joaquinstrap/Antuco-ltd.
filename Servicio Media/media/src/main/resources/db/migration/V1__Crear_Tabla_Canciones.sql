CREATE TABLE canciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    id_album VARCHAR(255) NOT NULL, -- Referencia al álbum en el servicio Catálogo
    nombre_archivo VARCHAR(255) NOT NULL, -- El nombre del archivo (ej: "cancion1.mp3")
    fecha_subida TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);