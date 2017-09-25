package br.com.apptrechos.torcidapremiada.repository.filters;

import java.time.LocalDate;

import br.com.apptrechos.torcidapremiada.model.Campeonato;
import br.com.apptrechos.torcidapremiada.model.EquipesAnapolinas;

public class PartidaFilter {
	private EquipesAnapolinas equipeAnapolina;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private Campeonato campeonato;
	
	public EquipesAnapolinas getEquipeAnapolina() {
		return equipeAnapolina;
	}
	
	public void setEquipeAnapolina(EquipesAnapolinas equipeAnapolina) {
		this.equipeAnapolina = equipeAnapolina;
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

	public Campeonato getCampeonato() {
		return campeonato;
	}
	
	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}
	
}
