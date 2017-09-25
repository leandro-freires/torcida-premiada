package br.com.apptrechos.torcidapremiada.repository.helper.imovel;

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

import br.com.apptrechos.torcidapremiada.model.Imovel;
import br.com.apptrechos.torcidapremiada.repository.filters.ImovelFilter;
import br.com.apptrechos.torcidapremiada.repository.paginacao.PaginacaoUtil;

public class ImoveisImpl implements ImoveisQueries {

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<Imovel> buscar(Imovel imovel) {
		return this.entityManager
				.createQuery("select i from Imovel i where " + "i.inscricaoImobiliaria = :inscricaoImobiliaria and "
						+ "i.condicaoDeResidente = :condicaoDeResidente and "
						+ "i.contribuinte.codigo = :codigoContribuinte", Imovel.class)
				.setParameter("inscricaoImobiliaria", imovel.getInscricaoImobiliaria().replaceAll("\\.", ""))
				.setParameter("condicaoDeResidente", imovel.getCondicaoDeResidente())
				.setParameter("codigoContribuinte", imovel.getContribuinte().getCodigo()).getResultList().stream()
				.findFirst();
	}

	@Override
	public Optional<Imovel> buscarAtivo(Imovel imovel) {
		return this.entityManager
				.createQuery("select i from Imovel i where " + "i.inscricaoImobiliaria = :inscricaoImobiliaria and "
						+ "i.status = :valorDoStatus", Imovel.class)
				.setParameter("inscricaoImobiliaria", imovel.getInscricaoImobiliaria().replaceAll("\\.", ""))
				.setParameter("valorDoStatus", true).getResultList().stream().findFirst();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public Page<Imovel> filtrar(ImovelFilter imovelFilter, Pageable pageable) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Imovel.class)
				.addOrder(Order.desc("codigo"));

		this.paginacaoUtil.preparar(criteria, pageable);

		adicionarFiltro(imovelFilter, criteria);

		List<Imovel> imoveisFiltrados = criteria.list();

		return new PageImpl<>(imoveisFiltrados, pageable, totalDeRegistros(imovelFilter));
	}

	private Long totalDeRegistros(ImovelFilter imovelFilter) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Imovel.class);
		adicionarFiltro(imovelFilter, criteria);
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(ImovelFilter imovelFilter, Criteria criteria) {
		if (imovelFilter != null) {
			if (!StringUtils.isEmpty(imovelFilter.getInscricaoImobiliaria())) {
				criteria.add(Restrictions.eq("inscricaoImobiliaria",
						imovelFilter.getInscricaoImobiliaria().replaceAll("\\.", "")));
			}

			if (isStatus(imovelFilter)) {
				criteria.add(Restrictions.eq("status", imovelFilter.getStatus()));
			}

			if (isSituacao(imovelFilter)) {
				criteria.add(Restrictions.eq("situacao", imovelFilter.getSituacao()));
			}
		}
	}

	private boolean isSituacao(ImovelFilter imovelFilter) {
		return imovelFilter.getSituacao() != null;
	}

	private boolean isStatus(ImovelFilter imovelFilter) {
		return imovelFilter.getStatus() != null;
	}

	@Override
	public Long totalDeImoveis() {
		Optional<Long> optional = Optional.ofNullable(entityManager
				.createQuery("select count(codigo) from Imovel i where i.status = :valorDoStatus", Long.class)
				.setParameter("valorDoStatus", true).getSingleResult());

		return optional.orElse(0L);
	}

}
