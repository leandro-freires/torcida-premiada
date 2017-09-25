package br.com.apptrechos.torcidapremiada.controller;

import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.apptrechos.torcidapremiada.model.DadosRecuperacao;
import br.com.apptrechos.torcidapremiada.model.Usuario;
import br.com.apptrechos.torcidapremiada.repository.Usuarios;
import br.com.apptrechos.torcidapremiada.service.UsuarioService;
import br.com.apptrechos.torcidapremiada.service.email.sender.EmailSender;

@Controller
@RequestMapping("/recuperacao")
public class RecuperaSenhaController {
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ModelAndView carregarPagina() {
		ModelAndView modelAndView = new ModelAndView("recuperacao/senha");
		
		return modelAndView;
	}
	
	@GetMapping("/consulta/{token}")
	public ModelAndView consultarToken(@PathVariable("token") String token) {
		ModelAndView modelAndView = new ModelAndView("recuperacao/cadastro");
		
//		Usuario usuario = this.usuarios.findByTokenSenha(token);
		
//		modelAndView.addObject("usuario", usuario);

		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> enviar(@RequestBody @Valid DadosRecuperacao dados, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("cpf").getDefaultMessage());
		}
		
		Optional<Usuario> usuarioOptional = this.usuarios.findByCpf(dados.getCpf().replaceAll("\\.|-", ""));
		
		if (!usuarioOptional.isPresent()) {
			String mensagem = "Usuário não cadastrado";
			return ResponseEntity.badRequest().body(mensagem);
		} else if (usuarioOptional.isPresent() && !usuarioOptional.get().getAtivo()) {
			String mensagem = "Conta de usuário não está ativa";
			return ResponseEntity.badRequest().body(mensagem);
		}
		
		Usuario usuario = usuarioOptional.get();
		
		usuario.setTokenSenha(gerarToken());
		
		this.usuarioService.inserirToken(usuario);
		
		EmailSender emailSender = new EmailSender();
		
		try {
			emailSender.enviarMensagem(usuarioOptional.get());
		} catch (MessagingException e) {
			String mensagem = "Erro ao tentar enviar a mensagem de recuparação de senha para " + usuarioOptional.get().getEmail();
			return ResponseEntity.badRequest().body(mensagem);
		}
		
		String mensagem = "Uma solicitação de alteração de senha foi enviada com sucesso para o e-mail " + usuarioOptional.get().getEmail();
		
		return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	private String gerarToken() {
		return UUID.randomUUID().toString().replaceAll("-", "");
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		return encoder.encode(uuid);
	}
	
}
