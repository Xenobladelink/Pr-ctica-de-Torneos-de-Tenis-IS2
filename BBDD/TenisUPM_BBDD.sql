CREATE DATABASE IF NOT EXISTS TenisUPM;
USE TenisUPM;

CREATE TABLE Usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50),
    apellidos VARCHAR(50),
    telefono VARCHAR(15),
    email VARCHAR(100) UNIQUE,
    nombre_usuario VARCHAR(50) UNIQUE,
    contrasena VARCHAR(255)
);

SELECT * FROM usuarios;