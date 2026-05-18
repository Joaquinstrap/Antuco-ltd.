-- Tabla principal del carrito (Una por usuario)
CREATE TABLE carrito (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id VARCHAR(255) NOT NULL UNIQUE, -- El ID del usuario (podría venir de Auth o sesion)
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultima_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de items dentro del carrito
CREATE TABLE item_carrito (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    carrito_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL, -- ID del producto en el microservicio de catálogo
    nombre_producto VARCHAR(255) NOT NULL, -- Snapshot del nombre
    precio_unitario DECIMAL(10, 2) NOT NULL, -- Snapshot del precio
    cantidad INT NOT NULL DEFAULT 1,
    imagen_url VARCHAR(255), -- Snapshot de la imagen
    CONSTRAINT fk_item_carrito FOREIGN KEY (carrito_id) REFERENCES carrito(id) ON DELETE CASCADE
);