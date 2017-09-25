package br.com.apptrechos.torcidapremiada.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apptrechos.torcidapremiada.model.Equipe;

public interface TimesAdversario extends JpaRepository<Equipe, Long> {
	Optional<Equipe> findByNomeIgnoreCase(String nome);
}
