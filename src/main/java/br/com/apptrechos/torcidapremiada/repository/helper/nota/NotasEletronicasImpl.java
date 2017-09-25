package br.com.apptrechos.torcidapremiada.repository.helper.nota;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.apptrechos.torcidapremiada.model.NotaEletronica;
import br.com.apptrechos.torcidapremiada.repository.filters.NotaEletronicaFilter;
import br.com.apptrechos.torcidapremiada.repository.paginacao.PaginacaoUtil;

public class NotasEletronicasImpl implements NotasEletronicasQueries {
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<NotaEletronica> buscar(NotaEletronica notaEletronica) {
		return this.entityManager.createQuery("select n from NotaEletronica n where "
				+ "n.contribuinte.codigo = :codigoContribuinte and "
				+ "n.inscricaoPrestador = :inscricaoPrestador and "
				+ "n.numero = :numero and "
				+ "n.codigoVerificacao = :codigoVerificacao and "
				+ "n.dataEmissao = :dataEmissao and "
				+ "n.tipoDeSerie = :tipoSerie", NotaEletronica.class)
				.setParameter("codigoContribuinte", notaEletronica.getContribuinte().getCodigo())
				.setParameter("inscricaoPrestador", notaEletronica.getInscricaoPrestador())
				.setParameter("numero", notaEletronica.getNumero())
				.setParameter("codigoVerificacao", notaEletronica.getCodigoVerificacao())
				.setParameter("dataEmissao", notaEletronica.getDataEmissao())
				.setParameter("tipoSerie", notaEletronica.getTipoDeSerie())
				.getResultList().stream().findFirst();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public Page<NotaEletronica> filtrar(NotaEletronicaFilter notaEletronicaFilter, Pageable pageable) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(NotaEletronica.class).addOrder(Order.desc("codigo"));
		
		this.paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(notaEletronicaFilter, criteria);
		
		List<NotaEletronica> notasEletronicasFiltradas = criteria.list();
		
		return new PageImpl<>(notasEletronicasFiltradas, pageable, totalDeRegistros(notaEletronicaFilter));
	}

	private Long totalDeRegistros(NotaEletronicaFilter notaEletronicaFilter) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(NotaEletronica.class);
		adicionarFiltro(notaEletronicaFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(NotaEletronicaFilter notaEletronicaFilter, Criteria criteria) {
		if (notaEletronicaFilter != null) {
			if (isTipoSerie(notaEletronicaFilter)) {
				criteria.add(Restrictions.eq("tipoDeSerie", notaEletronicaFilter.getTipoDeSerie()));
			}
			
			if (notaEletronicaFilter.getDataInicial() != null) {
				criteria.add(Restrictions.ge("dataEmissao", notaEletronicaFilter.getDataInicial()));
			}
			
			if (notaEletronicaFilter.getDataFinal() != null) {
				criteria.add(Restrictions.le("dataEmissao", notaEletronicaFilter.getDataFinal()));
			}
			
			if (!StringUtils.isEmpty(notaEletronicaFilter.getInscricaoPrestador())) {
				criteria.add(Restrictions.eq("inscricaoPrestador", notaEletronicaFilter.getInscricaoPrestador()));
			}
			
			if (notaEletronicaFilter.getNumero() != null) {
				criteria.add(Restrictions.eq("numero", notaEletronicaFilter.getNumero()));
			}
		}
	}

	private boolean isTipoSerie(NotaEletronicaFilter notaEletronicaFilter) {
		return notaEletronicaFilter.getTipoDeSerie() != null;
	}
	
	@Override
	public Long totalDeNotas() {
		Optional<Long> optional = Optional.ofNullable(entityManager
				.createQuery("select count(codigo) from NotaEletronica", Long.class)
				.getSingleResult());

		return optional.orElse(0L);
	}

}
