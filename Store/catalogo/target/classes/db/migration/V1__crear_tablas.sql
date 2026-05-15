-- Album virtual (para busqueda)
CREATE TABLE album (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    artista VARCHAR(255) NOT NULL,
    descripcion TEXT, -- "Este album de los charros de lumaco es muy god"
    portada_url VARCHAR(500) -- Link a la imagen jpg o algo
);

-- Merch (poleras, jockeys, etc)
CREATE TABLE merch (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL, -- "Polera con logo"
    descripcion TEXT,
    merch_url VARCHAR(500) -- Esto tambien es un link
);

-- Producto (esto se manda al carrito) (puede ser musica, o polera, chao productos en grupo me tienen chato)
CREATE TABLE producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    
    -- Una sola de estas se va a llenar acorde a la clave foranea
    album_id BIGINT NULL,
    merch_id BIGINT NULL,
    
    -- Data de producto
    sku VARCHAR(50) UNIQUE,  -- "BOC001-CD" o "ROPA001"
    precio DECIMAL(10, 2) NOT NULL,
    stock INT DEFAULT 0,
    
    -- JSON directo, muy facil
    -- Album: {"formato": "Vinilo", "peso": "180g"}
    -- Polera {"talla": "L", "color": "verde"}
    especificacion JSON,
    
    FOREIGN KEY (album_id) REFERENCES album(id),
    FOREIGN KEY (merch_id) REFERENCES merch(id)
);

-- POR AGREGAR: fecha de agregado. Es necesario eso va en el temario igual?