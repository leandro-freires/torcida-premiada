package br.com.apptrechos.torcidapremiada.repository.helper.partida;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.apptrechos.torcidapremiada.model.Partida;
import br.com.apptrechos.torcidapremiada.repository.filters.PartidaFilter;

public interface PartidasQueries {
	public List<Partida> buscarPartidas();
	public List<Partida> buscarPartidasDoAno();
	public Optional<Partida> buscar(Partida partida);
	public Page<Partida> filtrar(PartidaFilter partidaFilter, Pageable pageable);
//	public Partida buscarProximaPartida();
}
