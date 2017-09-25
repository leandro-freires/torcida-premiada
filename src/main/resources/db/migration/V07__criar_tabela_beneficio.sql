CREATE TABLE beneficio (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    numero_ingresso_a VARCHAR(5),
    numero_ingresso_b VARCHAR(5),
    codigo_nota BIGINT(20),
    codigo_imovel BIGINT(20),
    codigo_partida BIGINT(20),
    codigo_contribuinte BIGINT(20),
    status VARCHAR(100) NOT NULL,
    data_hora_entrega DATETIME,
    cpf_usuario VARCHAR(11),
    FOREIGN KEY (codigo_nota) REFERENCES nota_fiscal (codigo),
    FOREIGN KEY (codigo_imovel) REFERENCES imovel (codigo),
    FOREIGN KEY (codigo_partida) REFERENCES partida (codigo),
    FOREIGN KEY (codigo_contribuinte) REFERENCES contribuinte (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
