CREATE TABLE eventos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    fecha TIMESTAMP NOT NULL,
    lugar VARCHAR(255) NOT NULL,
    precio DOUBLE NOT NULL,
    capacidad_total INT NOT NULL
);

CREATE TABLE reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    evento_id BIGINT NOT NULL,
    usuario_username VARCHAR(255) NOT NULL,
    cantidad INT NOT NULL,
    fecha_reserva TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (evento_id) REFERENCES eventos(id)
);