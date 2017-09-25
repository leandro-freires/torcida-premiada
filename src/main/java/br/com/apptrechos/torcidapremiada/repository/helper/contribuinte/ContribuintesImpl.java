package br.com.apptrechos.torcidapremiada.repository.helper.contribuinte;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.apptrechos.torcidapremiada.model.Contribuinte;
import br.com.apptrechos.torcidapremiada.repository.filters.ContribuinteFilter;
import br.com.apptrechos.torcidapremiada.repository.paginacao.PaginacaoUtil;

public class ContribuintesImpl implements ContribuintesQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Contribuinte> filtrar(ContribuinteFilter contribuinteFilter, Pageable pageable) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Contribuinte.class).addOrder(Order.desc("codigo"));
		
		this.paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(contribuinteFilter, criteria);
		
		List<Contribuinte> contribuintesFiltrados = criteria.list();
		
		return new PageImpl<>(contribuintesFiltrados, pageable, totalDeRegistros(contribuinteFilter));
	}

	private Long totalDeRegistros(ContribuinteFilter contribuinteFilter) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Contribuinte.class);
		adicionarFiltro(contribuinteFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(ContribuinteFilter contribuinteFilter, Criteria criteria) {
		if (contribuinteFilter != null) {
			if (isTipoPessoa(contribuinteFilter)) {
				criteria.add(Restrictions.eq("tipoPessoa", contribuinteFilter.getTipoPessoa()));
			}
			
			if (!StringUtils.isEmpty(contribuinteFilter.getCpfOuCnpj())) {
				criteria.add(Restrictions.eq("cpfOuCnpj", contribuinteFilter.getCpfOuCnpj().replaceAll("\\.|-|/", "")));
			}
			
			if (!StringUtils.isEmpty(contribuinteFilter.getNome())) {
				criteria.add(Restrictions.ilike("nome", contribuinteFilter.getNome(), MatchMode.ANYWHERE));
			}
		}
		
	}

	private boolean isTipoPessoa(ContribuinteFilter contribuinteFilter) {
		return contribuinteFilter.getTipoPessoa() != null;
	}
	
	@Override
	public Long totalDeContribuintes() {
		Optional<Long> optional = Optional.ofNullable(entityManager.createQuery("select count(codigo) from Contribuinte", Long.class)
		.getSingleResult());
		
		return optional.orElse(0L);
	}

}
