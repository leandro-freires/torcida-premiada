CREATE TABLE imovel (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    inscricao_imobiliaria VARCHAR(13) NOT NULL,
    condicao_residente VARCHAR(200) NOT NULL,
    situacao VARCHAR(200) NOT NULL,
    status BOOLEAN DEFAULT true,
    codigo_contribuinte BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_contribuinte) REFERENCES contribuinte (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;