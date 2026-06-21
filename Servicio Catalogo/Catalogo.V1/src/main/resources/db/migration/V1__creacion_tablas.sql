-- 1. Tabla de Categorías (Referencia en la pizarra)
CREATE TABLE categorias (
    id VARCHAR(255) NOT NULL,
    nombre_categoria VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_nombre_categoria UNIQUE (nombre_categoria)
);

-- =========================================================================
-- 3. CREACIÓN DE LA TABLA PRODUCTOS (id_categoria ahora es VARCHAR(10))
-- =========================================================================
CREATE TABLE productosdelcatalogo (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre_producto VARCHAR(255) NOT NULL,
    id_categoria VARCHAR(255) NOT NULL, -- Debe coincidir exactamente con el tipo de la tabla categorias
    precio DOUBLE NOT NULL,
    dtype VARCHAR(31) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_productos_categoria FOREIGN KEY (id_categoria) REFERENCES categorias(id)
);

-- =========================================================================
-- 4. CREACIÓN DE ATRIBUTOS DINÁMICOS
-- =========================================================================
CREATE TABLE producto_atributos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    producto_id BIGINT NOT NULL,
    nombre_atributo VARCHAR(255) NOT NULL,
    valor_atributo VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_atributos_producto FOREIGN KEY (producto_id) REFERENCES productosdelcatalogo(id) ON DELETE CASCADE
);