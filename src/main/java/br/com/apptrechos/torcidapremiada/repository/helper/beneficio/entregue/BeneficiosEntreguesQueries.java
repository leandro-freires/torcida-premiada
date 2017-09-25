package br.com.apptrechos.torcidapremiada.repository.helper.beneficio.entregue;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.apptrechos.torcidapremiada.dto.IngressosPorPartidaDto;
import br.com.apptrechos.torcidapremiada.model.Beneficio;
import br.com.apptrechos.torcidapremiada.repository.filters.BeneficioFilter;

public interface BeneficiosEntreguesQueries {
	public Page<Beneficio> filtrar(BeneficioFilter beneficioEntregueFilter, Pageable pageable);
	public Long totalDeBeneficiosConcedidos();
	public List<IngressosPorPartidaDto> totalDeIngressosPorPartidaNoMes();
}
