CREATE DATABASE IF NOT EXISTS sistema_academico DEFAULT CHARACTER SET utf8mb4;

USE sistema_academico;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    curso VARCHAR(50) NOT NULL,
    semestre INT NOT NULL,
    pergunta_secreta VARCHAR(255) NOT NULL,
    resposta_secreta VARCHAR(255) NOT NULL,
    perfil_publico BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE publicacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    conteudo TEXT NOT NULL,
    imagem LONGBLOB NULL,
    link VARCHAR(255) NULL,
    tipo_midia ENUM('TEXTO', 'IMAGEM', 'VIDEO') DEFAULT 'TEXTO',
    data_publicacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB;

CREATE TABLE interacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    publicacao_id INT NOT NULL,
    usuario_id INT NOT NULL,
    tipo ENUM('CURTIDA', 'COMENTARIO') NOT NULL,
    conteudo TEXT NULL,
    moderado BOOLEAN DEFAULT FALSE,
    data_interacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (publicacao_id) REFERENCES publicacoes(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB;