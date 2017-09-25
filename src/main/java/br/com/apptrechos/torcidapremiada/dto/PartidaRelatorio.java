package br.com.apptrechos.torcidapremiada.dto;

import javax.validation.constraints.NotNull;

import br.com.apptrechos.torcidapremiada.model.Partida;

public class PartidaRelatorio {
	
	@NotNull(message = "Selecione uma partida para gerar o relatório")
	private Partida partida;

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}
}
