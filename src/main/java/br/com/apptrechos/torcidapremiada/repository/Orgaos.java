package br.com.apptrechos.torcidapremiada.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apptrechos.torcidapremiada.model.Orgao;

public interface Orgaos extends JpaRepository<Orgao, Long> {
	public Optional<Orgao> findByNomeIgnoreCase(String nome);
}
