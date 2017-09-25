package br.com.apptrechos.torcidapremiada.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apptrechos.torcidapremiada.model.Imovel;
import br.com.apptrechos.torcidapremiada.repository.helper.imovel.ImoveisQueries;

public interface Imoveis extends JpaRepository<Imovel, Long>, ImoveisQueries {
	public List<Imovel> findByCodigoIn(Long[] codigos);
	public Imovel findByCodigoIn(Long codigo);
}
