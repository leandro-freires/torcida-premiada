package br.com.apptrechos.torcidapremiada.model;

public enum TipoSerie {
	NOTA_ELETRONICA ("NOTA FISCAL ELETRÔNICA"),
	NOTA_ELETRONICA_AVULSA ("NOTA FISCAL ELETRÔNICA AVULSA");
	
	private String descricao;

	TipoSerie(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
