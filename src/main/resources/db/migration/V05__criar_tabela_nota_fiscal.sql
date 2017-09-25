CREATE TABLE nota_fiscal (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    tipo_serie VARCHAR(100) NOT NULL,
    data_emissao DATE NOT NULL,
    inscricao_prestador VARCHAR(10) NOT NULL,
    numero INTEGER NOT NULL,
    codigo_verificacao VARCHAR(9) NOT NULL,
    codigo_contribuinte BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_contribuinte) REFERENCES contribuinte (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;