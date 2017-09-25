package br.com.apptrechos.torcidapremiada.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.apptrechos.torcidapremiada.model.Contribuinte;
import br.com.apptrechos.torcidapremiada.repository.Contribuintes;
import br.com.apptrechos.torcidapremiada.service.exception.DadoJaCadastradoException;

@Service
public class ContribuinteService {

	@Autowired
	private Contribuintes contribuintes;
	
	@Transactional
	public void salvar(Contribuinte contribuinte) {
		Optional<Contribuinte> contribuinteExistente = this.contribuintes.findByCpfOuCnpj(contribuinte.getCpfOuCnpj());
		
		if (contribuinteExistente.isPresent() && !contribuinteExistente.get().equals(contribuinte)) {
			throw new DadoJaCadastradoException("Contribuinte já cadastrado");
		}
		
		this.contribuintes.save(contribuinte);
	}
	
}
