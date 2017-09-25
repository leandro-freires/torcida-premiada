package br.com.apptrechos.torcidapremiada.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.apptrechos.torcidapremiada.model.StatusUsuario;
import br.com.apptrechos.torcidapremiada.model.Usuario;
import br.com.apptrechos.torcidapremiada.repository.Usuarios;
import br.com.apptrechos.torcidapremiada.service.exception.DadoJaCadastradoException;
import br.com.apptrechos.torcidapremiada.service.exception.SenhaObrigatoriaException;

@Service
public class UsuarioService {

	@Autowired
	private Usuarios usuarios;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public void salvar(Usuario usuario) {
		Optional<Usuario> usuarioExistente = this.usuarios.findByCpf(usuario.getCpf().replaceAll("\\.|-|/", ""));

		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new DadoJaCadastradoException("Usuário já cadastrado");
		}

		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaException("Senha é obrigatória");
		}

		if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		} else if (StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(usuarioExistente.get().getSenha());
		}

		if (!usuario.isNovo() && usuario.getAtivo() == null) {
			usuario.setAtivo(usuarioExistente.get().getAtivo());
		}

		usuario.setConfirmacaoDeSenha(usuario.getSenha());
		
		if (!usuario.isNovo() && usuario.getAtivo() == null) {
			usuario.setAtivo(usuarioExistente.get().getAtivo());
		}

		this.usuarios.save(usuario);
	}
	
	@Transactional
	public void inserirToken(Usuario usuario) {
		this.usuarios.save(usuario);
	}

	@Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario status) {
		status.executar(codigos, this.usuarios);
	}
}
