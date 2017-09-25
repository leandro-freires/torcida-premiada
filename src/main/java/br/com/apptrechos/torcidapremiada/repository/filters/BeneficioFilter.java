package br.com.apptrechos.torcidapremiada.repository.filters;

import java.time.LocalDate;

public class BeneficioFilter {
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private String cpfOuCnpjContribuinte;
	
	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	public String getCpfOuCnpjContribuinte() {
		return cpfOuCnpjContribuinte;
	}
	
	public void setCpfOuCnpjContribuinte(String cpfOuContribuinte) {
		this.cpfOuCnpjContribuinte = cpfOuContribuinte;
	}
	
}
