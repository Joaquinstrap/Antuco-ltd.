CREATE TABLE inventarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_Id BIGINT NOT NULL UNIQUE,
    cantidad INT NOT NULL DEFAULT 0,
    tipo_producto VARCHAR(255) NOT NULL,

    /* Esta linea sirve como una validacion que dice que no puede haber una cantidad menor a 0 */
    CONSTRAINT check_cantidad_no_negativa CHECK (cantidad >= 0)
);