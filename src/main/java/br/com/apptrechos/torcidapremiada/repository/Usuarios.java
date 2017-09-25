package br.com.apptrechos.torcidapremiada.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apptrechos.torcidapremiada.model.Usuario;
import br.com.apptrechos.torcidapremiada.repository.helper.usuario.UsuariosQueries;

public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries {
	public Optional<Usuario> findByCpf(String cpf);
//	public Usuario findByTokenSenha(String tokenSenha);
	public List<Usuario> findByCodigoIn(Long[] codigos);	
}
