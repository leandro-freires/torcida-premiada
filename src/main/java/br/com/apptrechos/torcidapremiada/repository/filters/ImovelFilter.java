package br.com.apptrechos.torcidapremiada.repository.filters;

import br.com.apptrechos.torcidapremiada.model.Situacao;

public class ImovelFilter {
	private String inscricaoImobiliaria;
	private Situacao situacao;
	private Boolean status;
	
	public String getInscricaoImobiliaria() {
		return inscricaoImobiliaria;
	}
	
	public void setInscricaoImobiliaria(String inscricaoImobiliaria) {
		this.inscricaoImobiliaria = inscricaoImobiliaria;
	}
	
	public Situacao getSituacao() {
		return situacao;
	}
	
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
