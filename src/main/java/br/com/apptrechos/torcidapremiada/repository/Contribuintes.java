package br.com.apptrechos.torcidapremiada.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apptrechos.torcidapremiada.model.Contribuinte;
import br.com.apptrechos.torcidapremiada.repository.helper.contribuinte.ContribuintesQueries;

public interface Contribuintes extends JpaRepository<Contribuinte, Long>, ContribuintesQueries {
	public Optional<Contribuinte> findByCpfOuCnpj(String cpfOuCnpj);
	public Contribuinte findByCodigoIn(Long codigo);
}
