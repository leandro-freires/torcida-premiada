package br.com.apptrechos.torcidapremiada.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.apptrechos.torcidapremiada.model.Grupo;
import br.com.apptrechos.torcidapremiada.model.Usuario;
import br.com.apptrechos.torcidapremiada.model.UsuarioGrupo;
import br.com.apptrechos.torcidapremiada.repository.filters.UsuarioFilter;
import br.com.apptrechos.torcidapremiada.repository.paginacao.PaginacaoUtil;

public class UsuariosImpl implements UsuariosQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Override
	public Optional<Usuario> porCpfAtivo(String cpf) {
		return this.entityManager.createQuery("from Usuario where cpf = :cpf and ativo = true", Usuario.class)
			.setParameter("cpf",cpf).getResultList().stream().findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return this.entityManager
				.createQuery("select distinct p.nome from Usuario u inner join "
				+ "u.grupos g inner join g.permissoes p where u = :usuario", String.class)
				.setParameter("usuario", usuario)
				.getResultList();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Usuario.class);
		
		this.paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(usuarioFilter, criteria);
		
		List<Usuario> usuariosFiltrados = criteria.list();
		usuariosFiltrados.forEach(u -> Hibernate.initialize(u.getGrupos()));
		
		return new PageImpl<>(usuariosFiltrados, pageable, totalDeRegistros(usuarioFilter));
	}
	
	@Transactional(readOnly = true)
	@Override
	public Usuario buscarComGrupos(Long codigo) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Usuario.class);
		criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Usuario) criteria.uniqueResult();
	}
	
	private Long totalDeRegistros(UsuarioFilter usuarioFilter) {
		Criteria criteria = this.entityManager.unwrap(Session.class).createCriteria(Usuario.class);
		adicionarFiltro(usuarioFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(UsuarioFilter usuarioFilter, Criteria criteria) {
		if (usuarioFilter != null) {
			if (!StringUtils.isEmpty(usuarioFilter.getCpf())) {
				criteria.add(Restrictions.eq("cpf", usuarioFilter.getCpf().replaceAll("\\.|-|/", "")));
			}
			
			if (!StringUtils.isEmpty(usuarioFilter.getNome())) {
				criteria.add(Restrictions.ilike("nome", usuarioFilter.getNome(), MatchMode.ANYWHERE));
			}
			
			if (isOrgaoPresente(usuarioFilter)) {
				criteria.add(Restrictions.eq("orgao", usuarioFilter.getOrgao()));
			}
			
			if (usuarioFilter.getGrupos() != null && !usuarioFilter.getGrupos().isEmpty()) {
				List<Criterion> subqueries = new ArrayList<>();
				
				for (Long codigoDoGrupo : usuarioFilter.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UsuarioGrupo.class);
					detachedCriteria.add(Restrictions.eq("id.grupo.codigo", codigoDoGrupo));
					detachedCriteria.setProjection(Projections.property("id.usuario"));
					
					subqueries.add(Subqueries.propertyIn("codigo", detachedCriteria));
				}
				
				Criterion[] criterions = new Criterion[subqueries.size()];
				
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}
		}
	}

	private boolean isOrgaoPresente(UsuarioFilter usuarioFilter) {
		return usuarioFilter.getOrgao() != null;
	}

}
