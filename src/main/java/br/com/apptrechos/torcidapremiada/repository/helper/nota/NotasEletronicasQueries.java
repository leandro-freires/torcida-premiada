package br.com.apptrechos.torcidapremiada.repository.helper.nota;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.apptrechos.torcidapremiada.model.NotaEletronica;
import br.com.apptrechos.torcidapremiada.repository.filters.NotaEletronicaFilter;

public interface NotasEletronicasQueries {
	public Optional<NotaEletronica> buscar(NotaEletronica notaEletronica); 
	public Page<NotaEletronica> filtrar(NotaEletronicaFilter notaEletronicaFilter, Pageable pageable);
	public Long totalDeNotas();
}
