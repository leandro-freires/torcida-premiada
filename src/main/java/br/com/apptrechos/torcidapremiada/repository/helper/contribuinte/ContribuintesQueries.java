package br.com.apptrechos.torcidapremiada.repository.helper.contribuinte;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.apptrechos.torcidapremiada.model.Contribuinte;
import br.com.apptrechos.torcidapremiada.repository.filters.ContribuinteFilter;

public interface ContribuintesQueries {
	public Page<Contribuinte> filtrar(ContribuinteFilter contribuinteFilter, Pageable pageable);
	public Long totalDeContribuintes();
}
