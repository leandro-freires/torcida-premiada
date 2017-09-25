package br.com.apptrechos.torcidapremiada.repository.filters;

import br.com.apptrechos.torcidapremiada.model.TipoPessoa;

public class ContribuinteFilter {
	private TipoPessoa tipoPessoa;
	private String cpfOuCnpj;
	private String nome;
	
	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}
	
	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
