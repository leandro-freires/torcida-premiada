package br.com.apptrechos.torcidapremiada.repository.helper.beneficio.pendente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.apptrechos.torcidapremiada.model.Beneficio;
import br.com.apptrechos.torcidapremiada.model.Contribuinte;
import br.com.apptrechos.torcidapremiada.model.Imovel;
import br.com.apptrechos.torcidapremiada.model.NotaEletronica;
import br.com.apptrechos.torcidapremiada.model.Partida;
import br.com.apptrechos.torcidapremiada.repository.filters.BeneficioFilter;

public interface BeneficiosPendentesQueries {
	public Optional<Beneficio> buscarBeneficioConcedido(Imovel imovel, Partida partida);
	public Optional<Beneficio> buscarBeneficioConcedido(NotaEletronica notaEletronica);
	public List<Beneficio> buscarQuantidadeDeBeneficiosConcedidoPorContribuinte(Contribuinte contribuinte, Partida partida);
	public Page<Beneficio> filtrar(BeneficioFilter beneficioFilter, Pageable pageable);
}
