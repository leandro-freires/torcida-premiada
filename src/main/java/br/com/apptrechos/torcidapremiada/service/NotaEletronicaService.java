package br.com.apptrechos.torcidapremiada.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.apptrechos.torcidapremiada.model.NotaEletronica;
import br.com.apptrechos.torcidapremiada.repository.NotasEletronicas;
import br.com.apptrechos.torcidapremiada.service.exception.DadoJaCadastradoException;

@Service
public class NotaEletronicaService {
	
	@Autowired
	private NotasEletronicas notasEletronicas;
	
	@Transactional
	public NotaEletronica salvar(NotaEletronica notaEletronica) {
		
		if (notaEletronica.getDataEmissao().isAfter(LocalDate.now()) && notaEletronica.isNova()) {
			throw new DataInvalidaException("A data da nota fiscal eletrônica deve ser igual ou anterior a data de hoje");
		}
		
		Optional<NotaEletronica> notaEletronicaOptional = this.notasEletronicas.buscar(notaEletronica);
		
		if (notaEletronicaOptional.isPresent() && !notaEletronicaOptional.get().equals(notaEletronica)) {
			throw new DadoJaCadastradoException("Nota Fiscal já cadastrada");
		}
		
		return this.notasEletronicas.saveAndFlush(notaEletronica);
	}
}
