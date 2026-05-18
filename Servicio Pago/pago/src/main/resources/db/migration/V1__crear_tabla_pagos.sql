CREATE TABLE IF NOT EXISTS pago (
    id_pago BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL UNIQUE, -- El id del usuario al que esta relacionado el pago
    id_carro BIGINT NOT NULL UNIQUE,
    status_pago VARCHAR(25) NOT NULL
)