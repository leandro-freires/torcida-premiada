package br.com.apptrechos.torcidapremiada.model;

public enum Campeonato {
	GOIANAO_1_DIV ("CAMPEONATO GOIANO DE FUTEBOL - 1º DIVISÃO"),
	GOIANAO_2_DIV ("CAMPEONATO GOIANO DE FUTEBOL - 2º DIVISÃO"),
	CAMPEONATO_BRASILEIRO ("CAMPEONATO BRASILEIRO DE FUTEBOL - SÉRIE D"),
	COPA_BRASIL ("COPA DO BRASIL DE FUTEBOL"),
	COPA_VERDE ("COPA VERDE");
	
	private String descricao;
	
	Campeonato(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
