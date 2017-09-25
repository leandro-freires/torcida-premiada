package br.com.apptrechos.torcidapremiada.model;

public enum Situacao {
	POSITIVO ("CERTIDÃO POSITIVA"),
	POSITIVO_EFEITO_NEGATIVO ("CERTIDÃO POSITIVA COM EFEITO NEGATIVO"),
	NEGATIVO ("CERTIDÃO NEGATIVA");
	
	private String descricao;
	
	Situacao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
