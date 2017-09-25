package br.com.apptrechos.torcidapremiada.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apptrechos.torcidapremiada.model.Beneficio;
import br.com.apptrechos.torcidapremiada.repository.helper.beneficio.pendente.BeneficiosPendentesQueries;

public interface BeneficiosPendentes extends JpaRepository<Beneficio, Long>, BeneficiosPendentesQueries {
	public Beneficio findByCodigo(Long codigo);
}
