package br.com.apptrechos.torcidapremiada.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.apptrechos.torcidapremiada.model.Beneficio;
import br.com.apptrechos.torcidapremiada.model.Status;
import br.com.apptrechos.torcidapremiada.repository.BeneficiosPendentes;

@Service
public class BeneficioService {
	
	@Autowired
	private BeneficiosPendentes beneficios;
	
	@Transactional
	public void salvar(Beneficio beneficio) {
		if (beneficio.isNovo()) {
			beneficio.setStatus(Status.PENDENTE);
		}

		this.beneficios.save(beneficio);
	}

}
