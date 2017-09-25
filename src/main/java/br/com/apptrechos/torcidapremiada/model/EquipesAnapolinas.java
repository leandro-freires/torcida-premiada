package br.com.apptrechos.torcidapremiada.model;

public enum EquipesAnapolinas {
	AFC ("ANÁPOLIS FUTEBOL CLUBE"),
	AAA ("ASSOCIAÇÃO ATLÉTICA ANAPOLINA"),
	GEA	("GRÊMIO ESPORTIVO ANÁPOLIS");
	
	private String descricao;
	
	EquipesAnapolinas(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
