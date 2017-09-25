package br.com.apptrechos.torcidapremiada.repository.filters;

import java.time.LocalDate;

import br.com.apptrechos.torcidapremiada.model.TipoSerie;

public class NotaEletronicaFilter {
	private TipoSerie tipoDeSerie;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private String inscricaoPrestador;
	private Integer numero;
	
	public TipoSerie getTipoDeSerie() {
		return tipoDeSerie;
	}
	
	public void setTipoDeSerie(TipoSerie tipoDeSerie) {
		this.tipoDeSerie = tipoDeSerie;
	}
	
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

	public String getInscricaoPrestador() {
		return inscricaoPrestador;
	}
	
	public void setInscricaoPrestador(String inscricaoPrestador) {
		this.inscricaoPrestador = inscricaoPrestador;
	}
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
}
