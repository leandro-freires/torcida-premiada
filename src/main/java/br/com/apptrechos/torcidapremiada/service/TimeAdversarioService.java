package br.com.apptrechos.torcidapremiada.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.apptrechos.torcidapremiada.model.Equipe;
import br.com.apptrechos.torcidapremiada.repository.TimesAdversario;
import br.com.apptrechos.torcidapremiada.service.exception.DadoJaCadastradoException;

@Service
public class TimeAdversarioService {
	
	@Autowired
	private TimesAdversario timesAdversario;
	
	@Transactional
	public Equipe salvar(Equipe equipe) {
		Optional<Equipe> equipeOptional = this.timesAdversario.findByNomeIgnoreCase(equipe.getNome());
		
		if (equipeOptional.isPresent()) {
			throw new DadoJaCadastradoException("Equipe já cadastrada");	
		}
		
		return this.timesAdversario.saveAndFlush(equipe);
	}
}
