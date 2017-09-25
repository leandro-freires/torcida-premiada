package br.com.apptrechos.torcidapremiada.repository.helper.imovel;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.apptrechos.torcidapremiada.model.Imovel;
import br.com.apptrechos.torcidapremiada.repository.filters.ImovelFilter;

public interface ImoveisQueries {
	public Optional<Imovel> buscar(Imovel imovel);
	public Optional<Imovel> buscarAtivo(Imovel imovel);
	public Page<Imovel> filtrar(ImovelFilter imovelFilter, Pageable pageable);
	public Long totalDeImoveis();
}
