package br.com.apptrechos.torcidapremiada.repository.helper.partida;

import java.time.LocalDate;
import java.time.Month;
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

import br.com.apptrechos.torcidapremiada.model.Partida;
import br.com.apptrechos.torcidapremiada.repository.filters.PartidaFilter;
import br.com.apptrechos.torcidapremiada.repository.paginacao.PaginacaoUtil;

public class PartidasImpl implements PartidasQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@Override
	public Optional<Partida> buscar(Partida partida) {
		return this.entityManager.createQuery("select p from Partida p where "
				+ "p.equipeAnapolina = :equipeAnapolina and "
				+ "p.equipeAdversaria.codigo = :codigoEquipeAdversaria and "
				+ "p.data = :data and "
				+ "p.campeonato = :campeonato", Partida.class)
		.setParameter("equipeAnapolina", partida.getEquipeAnapolina())
		.setParameter("codigoEquipeAdversaria", partida.getEquipeAdversaria().getCodigo())
		.setParameter("data", partida.getData())
		.setParameter("campeonato", partida.getCampeonato())
		.getResultList().stream().findFirst();
	}
	
//	@Transactional(readOnly = true)
//	@Override
//	public Partida buscarProximaPartida() {
//		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Partida.class);
//		
//		criteria.add(Restrictions.ge("data", LocalDate.now())).addOrder(Order.asc("data"));
//		
//		return (Partida) criteria.list().get(0);
//	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Partida> buscarPartidasDoAno() {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Partida.class);
		
		criteria.add(Restrictions.ge("data", LocalDate.now())).addOrder(Order.asc("data"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Partida> buscarPartidas() {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Partida.class);
		
		criteria.add(Restrictions.ge("data", LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 1))).addOrder(Order.asc("data"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Partida> filtrar(PartidaFilter partidaFilter, Pageable pageable) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Partida.class);
		
		this.paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(partidaFilter, criteria);
		
		List<Partida> partidasFiltradas = criteria.list();
		
		return new PageImpl<>(partidasFiltradas, pageable, totalDeRegistros(partidaFilter));
	}

	private Long totalDeRegistros(PartidaFilter partidaFilter) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Partida.class);
		adicionarFiltro(partidaFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(PartidaFilter partidaFilter, Criteria criteria) {
		if (partidaFilter != null) {
			if (isEquipeAnapolinaPresente(partidaFilter)) {
				criteria.add(Restrictions.eq("equipeAnapolina", partidaFilter.getEquipeAnapolina()));
			}
			
			if (partidaFilter.getDataInicial() != null) {
				criteria.add(Restrictions.ge("data", partidaFilter.getDataInicial()));
			}
			
			if (partidaFilter.getDataFinal() != null) {
				criteria.add(Restrictions.le("data", partidaFilter.getDataFinal()));
			}
			
			if (isCampeonato(partidaFilter)) {
				criteria.add(Restrictions.eq("campeonato", partidaFilter.getCampeonato()));
			}
		}
	}

	private boolean isCampeonato(PartidaFilter partidaFilter) {
		return partidaFilter.getCampeonato() != null;
	}

	private boolean isEquipeAnapolinaPresente(PartidaFilter partidaFilter) {
		return partidaFilter.getEquipeAnapolina() != null;
	}

}
