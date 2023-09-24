DROP TABLE IF EXISTS TAREA;
DROP TABLE IF EXISTS USUARIO;

CREATE TABLE USUARIO (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  nombre varchar(50) NOT NULL,
  email varchar(50) NOT NULL UNIQUE,
  clave varchar(255) NOT NULL,
  rol varchar(10) NOT NULL,
  fecha_crea timestamp NOT NULL
);

CREATE TABLE TAREA (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
  titulo varchar(100) NOT NULL,
  descripcion varchar(255) DEFAULT NULL,
  estado varchar(10) NOT NULL,
  prioridad int NOT NULL,
  fecha_crea timestamp NOT NULL,
  fecha_limite date NOT NULL,
  responsable_id int DEFAULT NULL,
  FOREIGN KEY (responsable_id) REFERENCES usuario(id)
);