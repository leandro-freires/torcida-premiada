package br.com.apptrechos.torcidapremiada.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.apptrechos.torcidapremiada.model.Usuario;
import br.com.apptrechos.torcidapremiada.repository.filters.UsuarioFilter;

public interface UsuariosQueries {
	public Optional<Usuario> porCpfAtivo(String cpf);
	public List<String> permissoes(Usuario usuario);
	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable);
	public Usuario buscarComGrupos(Long codigo);
	
}
