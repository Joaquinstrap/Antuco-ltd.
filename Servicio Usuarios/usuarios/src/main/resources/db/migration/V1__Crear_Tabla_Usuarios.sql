CREATE TABLE usuarios (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(255) NOT NULL UNIQUE,
    clave       VARCHAR(255) NOT NULL,
    rol         VARCHAR(255) -- Permisos del usuario. "User", "Admin", etc.
);
