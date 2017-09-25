package br.com.apptrechos.torcidapremiada.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apptrechos.torcidapremiada.model.Beneficio;
import br.com.apptrechos.torcidapremiada.repository.helper.beneficio.entregue.BeneficiosEntreguesQueries;

public interface BeneficiosEntregues extends JpaRepository<Beneficio, Long>, BeneficiosEntreguesQueries {

}
