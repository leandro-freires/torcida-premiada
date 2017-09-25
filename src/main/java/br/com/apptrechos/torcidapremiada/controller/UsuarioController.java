package br.com.apptrechos.torcidapremiada.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.apptrechos.torcidapremiada.controller.page.PageWrapper;
import br.com.apptrechos.torcidapremiada.model.Usuario;
import br.com.apptrechos.torcidapremiada.repository.Grupos;
import br.com.apptrechos.torcidapremiada.repository.Orgaos;
import br.com.apptrechos.torcidapremiada.repository.Usuarios;
import br.com.apptrechos.torcidapremiada.repository.filters.UsuarioFilter;
import br.com.apptrechos.torcidapremiada.service.UsuarioService;
import br.com.apptrechos.torcidapremiada.service.exception.DadoJaCadastradoException;
import br.com.apptrechos.torcidapremiada.service.exception.SenhaObrigatoriaException;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private Grupos grupos;
	
	@Autowired
	private Orgaos orgaos;
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/cadastro")
	public ModelAndView carregarPagina(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("usuario/cadastro");
		modelAndView.addObject("grupos", this.grupos.findAll());
		modelAndView.addObject("orgaos", this.orgaos.findAll());
		
		return modelAndView;
	}
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return carregarPagina(usuario);
		}
		
		try {
			this.usuarioService.salvar(usuario);
		} catch (DadoJaCadastradoException e) {
			result.rejectValue("cpf", e.getMessage(), e.getMessage());
			return carregarPagina(usuario);
		} catch (SenhaObrigatoriaException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return carregarPagina(usuario);
		}
		
		attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
		return new ModelAndView("redirect:/usuario/cadastro");
	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult result, 
			@PageableDefault(size=10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView("usuario/consulta");
		modelAndView.addObject("grupos", this.grupos.findAll());
		modelAndView.addObject("orgaos", this.orgaos.findAll());
		
		PageWrapper<Usuario> pageWrapper = new PageWrapper<>(usuarios.filtrar(usuarioFilter, pageable), httpServletRequest);
		
		modelAndView.addObject("pagina", pageWrapper);
		
		return modelAndView;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Usuario usuario = this.usuarios.buscarComGrupos(codigo);
		
		ModelAndView modelAndView = carregarPagina(usuario);
		modelAndView.addObject("usuario", usuario);
		
		return modelAndView;
	}
	
}
