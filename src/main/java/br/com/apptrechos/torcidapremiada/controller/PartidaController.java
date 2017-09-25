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
import br.com.apptrechos.torcidapremiada.model.Campeonato;
import br.com.apptrechos.torcidapremiada.model.EquipesAnapolinas;
import br.com.apptrechos.torcidapremiada.model.Partida;
import br.com.apptrechos.torcidapremiada.repository.Equipes;
import br.com.apptrechos.torcidapremiada.repository.Partidas;
import br.com.apptrechos.torcidapremiada.repository.filters.PartidaFilter;
import br.com.apptrechos.torcidapremiada.service.DataInvalidaException;
import br.com.apptrechos.torcidapremiada.service.PartidaService;
import br.com.apptrechos.torcidapremiada.service.exception.DadoJaCadastradoException;

@Controller
@RequestMapping("/partida")
public class PartidaController {
	
	@Autowired
	private Partidas partidas;
	
	@Autowired
	private Equipes equipes;
	
	@Autowired
	private PartidaService partidaService;

	@GetMapping("/cadastro")
	public ModelAndView carregarPagina(Partida partida) {
		ModelAndView modelAndView = new ModelAndView("partida/cadastro");
		modelAndView.addObject("equipesAnapolinas", EquipesAnapolinas.values());
		modelAndView.addObject("equipesAdversarias", this.equipes.findAll());
		modelAndView.addObject("capeonatos", Campeonato.values());
		
		return modelAndView;
	}
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Partida partida, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return carregarPagina(partida);
		}
		
		try {
			this.partidaService.salvar(partida);
		} catch (DataInvalidaException e) {
			result.rejectValue("data", e.getMessage(), e.getMessage());
			return carregarPagina(partida);
		} catch (DadoJaCadastradoException e) {
			result.rejectValue(null, e.getMessage(), e.getMessage());
			return carregarPagina(partida);
		}
		
		attributes.addFlashAttribute("mensagem", "Partida cadastrada com sucesso!");
		return new ModelAndView("redirect:/partida/cadastro");
	}
	
	@GetMapping
	public ModelAndView pesquisar(PartidaFilter partidaFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView("partida/consulta");
		modelAndView.addObject("equipesAnapolinas", EquipesAnapolinas.values());
		modelAndView.addObject("capeonatos", Campeonato.values());
		
		PageWrapper<Partida> pageWrapper = new PageWrapper<>(this.partidas.filtrar(partidaFilter, pageable), httpServletRequest);
		
		modelAndView.addObject("pagina", pageWrapper);
		
		return modelAndView;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Partida partida = this.partidas.findByCodigoIn(codigo);
		
		ModelAndView modelAndView = carregarPagina(partida);
		modelAndView.addObject("partida", partida);
		
		return modelAndView;
	}
	
}
