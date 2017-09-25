package br.com.apptrechos.torcidapremiada.repository.helper.beneficio.entregue;

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

import br.com.apptrechos.torcidapremiada.dto.IngressosPorPartidaDto;
import br.com.apptrechos.torcidapremiada.model.Beneficio;
import br.com.apptrechos.torcidapremiada.model.Status;
import br.com.apptrechos.torcidapremiada.repository.filters.BeneficioFilter;
import br.com.apptrechos.torcidapremiada.repository.paginacao.PaginacaoUtil;

public class BeneficiosEntreguesImpl implements BeneficiosEntreguesQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public Page<Beneficio> filtrar(BeneficioFilter beneficioFilter, Pageable pageable) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Beneficio.class)
				.add(Restrictions.eq("status", Status.ENTREGUE))
				.addOrder(Order.asc("p.data"));
		
		this.paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(beneficioFilter, criteria);
		
		List<Beneficio> beneficiosFiltrados = criteria.list();
		
		return new PageImpl<>(beneficiosFiltrados, pageable, totalDeRegistros(beneficioFilter));
	}

	private Long totalDeRegistros(BeneficioFilter beneficioFilter) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Beneficio.class).add(Restrictions.eq("status", Status.ENTREGUE));
		adicionarFiltro(beneficioFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(BeneficioFilter beneficioFilter, Criteria criteria) {
		criteria
			.createAlias("contribuinte", "c")
			.createAlias("partida", "p");

		if (beneficioFilter != null) {
			if (!StringUtils.isEmpty(beneficioFilter.getCpfOuCnpjContribuinte())) {
				criteria.add(Restrictions.eq("c.cpfOuCnpj", beneficioFilter.getCpfOuCnpjContribuinte().replaceAll("\\.|-|/", "")));
			}
			
			if (beneficioFilter.getDataInicial() != null) {
				criteria.add(Restrictions.ge("p.data", beneficioFilter.getDataInicial()));
			}
			
			if (beneficioFilter.getDataFinal() != null) {
				criteria.add(Restrictions.le("p.data", beneficioFilter.getDataFinal()));
			}
			
		}
	}
	
	@Override
	public Long totalDeBeneficiosConcedidos() {
		Optional<Long> optional = Optional.ofNullable(this.entityManager
				.createQuery("select count(codigo) * 2 from Beneficio b where b.status = :valorDoStatus", Long.class)
				.setParameter("valorDoStatus", Status.ENTREGUE).getSingleResult());

		return optional.orElse(0L);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IngressosPorPartidaDto> totalDeIngressosPorPartidaNoMes() {
		List<IngressosPorPartidaDto> totalDeIngressosPorPartidaNoMes = this.entityManager.createNamedQuery("Beneficio.totalDeIngressosPorPartidaNoMes").getResultList();
		return totalDeIngressosPorPartidaNoMes;
	}
}
