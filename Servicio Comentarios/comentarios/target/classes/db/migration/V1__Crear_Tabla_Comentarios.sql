CREATE TABLE comentarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id BIGINT NOT NULL, -- ID del producto en el servicio Catálogo
    usuario_username VARCHAR(255) NOT NULL, -- Username del usuario (del servicio Usuarios)
    texto TEXT NOT NULL,
    calificacion INT NOT NULL, -- Estrellas (1 a 5)
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);