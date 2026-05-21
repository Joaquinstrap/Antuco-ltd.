CREATE TABLE pago (
    id_pago BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario VARCHAR(255) NOT NULL, -- El id del usuario al que esta relacionado el pago
    nom_usuario VARCHAR(255) NOT NULL,
    id_carro BIGINT NOT NULL, -- el carro del que saca las cosas
    fecha_pago TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status_pago VARCHAR(255) NOT NULL DEFAULT 'PROCESANDO'
);

CREATE TABLE items_pago (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pago_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    nombre_producto VARCHAR(255) NOT NULL,
    precio_unidad DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_pago_boleta FOREIGN KEY (pago_id) REFERENCES pago(id_pago) ON DELETE CASCADE
);