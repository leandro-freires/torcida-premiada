package br.com.apptrechos.torcidapremiada.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.apptrechos.torcidapremiada.model.Partida;
import br.com.apptrechos.torcidapremiada.repository.Partidas;
import br.com.apptrechos.torcidapremiada.service.exception.DadoJaCadastradoException;

@Service
public class PartidaService {
	
	@Autowired
	private Partidas partidas;
	
	@Transactional
	public void salvar(Partida partida) {
		
		if (partida.getData().isBefore(LocalDate.now())) {
			throw new DataInvalidaException("A data da partida deve ser igual ou superior a data de hoje");
		}
		
		Optional<Partida> partidaOptional = this.partidas.buscar(partida);
		
		if (partidaOptional.isPresent() && !partidaOptional.get().equals(partida)) {
			throw new DadoJaCadastradoException("Partida já cadastrada");
		}
		
		this.partidas.save(partida);
	}
	
}
