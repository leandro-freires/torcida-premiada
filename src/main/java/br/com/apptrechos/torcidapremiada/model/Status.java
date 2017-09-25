package br.com.apptrechos.torcidapremiada.model;

public enum Status {
	PENDENTE ("PENDENTE"),
	ENTREGUE ("ENTREGUE"),
	INVALIDO ("INVÁLIDO");

	private String descricao;
	
	Status(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
