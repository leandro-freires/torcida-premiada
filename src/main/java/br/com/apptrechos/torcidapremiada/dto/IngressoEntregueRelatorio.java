package br.com.apptrechos.torcidapremiada.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.apptrechos.torcidapremiada.model.Partida;

public class IngressoEntregueRelatorio {
	@NotNull(message = "Selecione uma partida para gerar o relatório")
	private Partida partida;
	
	@NotBlank(message = "Insira o número do ingresso para gerar o relatório")
	private String numeroDoIngresso;
	
	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public String getNumeroDoIngresso() {
		return numeroDoIngresso;
	}

	public void setNumeroDoIngresso(String numeroDoIngresso) {
		this.numeroDoIngresso = numeroDoIngresso;
	}
	
}
