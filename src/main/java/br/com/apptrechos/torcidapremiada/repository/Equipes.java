package br.com.apptrechos.torcidapremiada.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apptrechos.torcidapremiada.model.Equipe;

public interface Equipes extends JpaRepository<Equipe, Long> {

}
