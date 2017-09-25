package br.com.apptrechos.torcidapremiada.repository.helper.beneficio.pendente;

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

import br.com.apptrechos.torcidapremiada.model.Beneficio;
import br.com.apptrechos.torcidapremiada.model.Contribuinte;
import br.com.apptrechos.torcidapremiada.model.Imovel;
import br.com.apptrechos.torcidapremiada.model.NotaEletronica;
import br.com.apptrechos.torcidapremiada.model.Partida;
import br.com.apptrechos.torcidapremiada.model.Status;
import br.com.apptrechos.torcidapremiada.repository.filters.BeneficioFilter;
import br.com.apptrechos.torcidapremiada.repository.paginacao.PaginacaoUtil;

public class BeneficiosPendentesImpl implements BeneficiosPendentesQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Override
	public Optional<Beneficio> buscarBeneficioConcedido(Imovel imovel, Partida partida) {
		return this.entityManager.createQuery("select b from Beneficio b where "
				+ "b.partida.codigo = :codigoDaPartida and "
				+ "b.imovel.contribuinte.codigo = :codigoDoContribuinte and "
				+ "b.imovel.codigo = :codigoDoImovel", Beneficio.class)
		.setParameter("codigoDaPartida", partida.getCodigo())
		.setParameter("codigoDoContribuinte", imovel.getContribuinte().getCodigo())
		.setParameter("codigoDoImovel", imovel.getCodigo())
		.getResultList().stream().findFirst();
	}
	
	@Override
	public Optional<Beneficio> buscarBeneficioConcedido(NotaEletronica notaEletronica) {
		return this.entityManager.createQuery("select b from Beneficio b where "
				+ "b.notaEletronica.codigo = :codigoDaNota and "
				+ "b.status = :status", Beneficio.class)
		.setParameter("codigoDaNota", notaEletronica.getCodigo())
		.setParameter("status", Status.ENTREGUE)
		.getResultList().stream().findFirst();
	}
	
	@Override
	public List<Beneficio> buscarQuantidadeDeBeneficiosConcedidoPorContribuinte(Contribuinte contribuinte, Partida partida) {
		return this.entityManager.createQuery("select b from Beneficio b where "
				+ "b.partida.codigo = :codigoDaPartida and "
				+ "b.contribuinte.codigo = :codigoDoContribuinte", Beneficio.class)
		.setParameter("codigoDaPartida", partida.getCodigo())
		.setParameter("codigoDoContribuinte", contribuinte.getCodigo())
		.getResultList();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public Page<Beneficio> filtrar(BeneficioFilter beneficioFilter, Pageable pageable) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Beneficio.class)
				.add(Restrictions.eq("status", Status.PENDENTE))
				.addOrder(Order.desc("codigo"));
		
		this.paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(beneficioFilter, criteria);
		
		List<Beneficio> beneficiosFiltrados = criteria.list();
		
		return new PageImpl<>(beneficiosFiltrados, pageable, totalDeRegistros(beneficioFilter));
	}

	private Long totalDeRegistros(BeneficioFilter beneficioFilter) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Beneficio.class).add(Restrictions.eq("status", Status.PENDENTE));
		adicionarFiltro(beneficioFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(BeneficioFilter beneficioFilter, Criteria criteria) {
		criteria
		.createAlias("contribuinte", "c");
		
		if (beneficioFilter != null) {
			if (!StringUtils.isEmpty(beneficioFilter.getCpfOuCnpjContribuinte())) {
				criteria.add(Restrictions.eq("c.cpfOuCnpj", beneficioFilter.getCpfOuCnpjContribuinte().replaceAll("\\.|-|/", "")));
			}
		}
	}
	
}
