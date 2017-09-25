package br.com.apptrechos.torcidapremiada.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apptrechos.torcidapremiada.model.NotaEletronica;
import br.com.apptrechos.torcidapremiada.repository.helper.nota.NotasEletronicasQueries;

public interface NotasEletronicas extends JpaRepository<NotaEletronica, Long>, NotasEletronicasQueries {
	public NotaEletronica findByCodigoIn(Long codigo);
}
