package br.com.apptrechos.torcidapremiada.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apptrechos.torcidapremiada.model.Partida;
import br.com.apptrechos.torcidapremiada.repository.helper.partida.PartidasQueries;

public interface Partidas extends JpaRepository<Partida, Long>, PartidasQueries {
	public Partida findByCodigoIn(Long codigo);
}
