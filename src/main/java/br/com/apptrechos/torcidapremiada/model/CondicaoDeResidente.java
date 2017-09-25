package br.com.apptrechos.torcidapremiada.model;

public enum CondicaoDeResidente {
	INQUILINO ("INQUILINO"),
	PROPRIETARIO ("PROPRIETÁRIO");
	
	private String descricao;
	
	CondicaoDeResidente(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
