package br.com.apptrechos.torcidapremiada.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.apptrechos.torcidapremiada.model.Imovel;
import br.com.apptrechos.torcidapremiada.model.StatusImovel;
import br.com.apptrechos.torcidapremiada.repository.Imoveis;
import br.com.apptrechos.torcidapremiada.service.exception.DadoJaCadastradoException;

@Service
public class ImovelService {

	@Autowired
	private Imoveis imoveis;
	
	@Transactional
	public Imovel salvar(Imovel imovel) {
		Optional<Imovel> buscarAtivo = this.imoveis.buscarAtivo(imovel);
		
		if (buscarAtivo.isPresent() && imovel.isNovo()) {
			Imovel imovelAtivoEncontrado = buscarAtivo.get();
			throw new DadoJaCadastradoException("Imóvel já está cadastrado e ativo para o contribuinte " + imovelAtivoEncontrado.getContribuinte().getCpfOuCnpj());
		}
		
		if (buscarAtivo.isPresent() && !buscarAtivo.get().getCodigo().equals(imovel.getCodigo())) {
			Imovel imovelAtivoEncontrado = buscarAtivo.get();
			throw new DadoJaCadastradoException("Imóvel já está cadastrado e ativo para o contribuinte " + imovelAtivoEncontrado.getContribuinte().getCpfOuCnpj());
		}

		Optional<Imovel> imovelOptional = this.imoveis.buscar(imovel);
		
		if (imovelOptional.isPresent() && !imovelOptional.get().equals(imovel)) {
			Imovel imovelOptionalEncontrado = imovelOptional.get();
			throw new DadoJaCadastradoException("Imóvel já cadastrado para o contribuinte " + imovelOptionalEncontrado.getContribuinte().getCpfOuCnpj());
		}
		
		return this.imoveis.saveAndFlush(imovel);
	}

	@Transactional
	public void alterarStatus(Long[] codigos, StatusImovel status) {
		status.executar(codigos, this.imoveis);
	}
	
}
