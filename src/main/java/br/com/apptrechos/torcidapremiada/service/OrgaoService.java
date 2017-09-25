package br.com.apptrechos.torcidapremiada.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.apptrechos.torcidapremiada.model.Orgao;
import br.com.apptrechos.torcidapremiada.repository.Orgaos;
import br.com.apptrechos.torcidapremiada.service.exception.DadoJaCadastradoException;

@Service
public class OrgaoService {
	
	@Autowired
	private Orgaos orgaos;
	
	@Transactional
	public Orgao salvar(Orgao orgao) {
		Optional<Orgao> orgaoOptional = this.orgaos.findByNomeIgnoreCase(orgao.getNome());
		
		if (orgaoOptional.isPresent()) {
			throw new DadoJaCadastradoException("Órgão já cadastrado");	
		}
		
		return this.orgaos.saveAndFlush(orgao);
	}
	
}
