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
import br.com.apptrechos.torcidapremiada.model.Beneficio;
import br.com.apptrechos.torcidapremiada.model.NotaEletronica;
import br.com.apptrechos.torcidapremiada.model.TipoPessoa;
import br.com.apptrechos.torcidapremiada.model.TipoSerie;
import br.com.apptrechos.torcidapremiada.repository.NotasEletronicas;
import br.com.apptrechos.torcidapremiada.repository.filters.NotaEletronicaFilter;
import br.com.apptrechos.torcidapremiada.service.BeneficioService;
import br.com.apptrechos.torcidapremiada.service.DataInvalidaException;
import br.com.apptrechos.torcidapremiada.service.NotaEletronicaService;
import br.com.apptrechos.torcidapremiada.service.exception.DadoJaCadastradoException;

@Controller
@RequestMapping("/nota")
public class NotaFiscalController {

	@Autowired
	private NotasEletronicas notasEletronicas;
	
	@Autowired
	private NotaEletronicaService notaEletronicaService;
	
	@Autowired
	private BeneficioService beneficioService;
	
	@GetMapping("/cadastro")
	public ModelAndView carregarPagina(NotaEletronica notaEletronica) {
		ModelAndView modelAndView = new ModelAndView("nota/cadastro");
		modelAndView.addObject("tiposDeSeries", TipoSerie.values());
		modelAndView.addObject("tiposPessoas", TipoPessoa.values());
		
		return modelAndView;
	}
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid NotaEletronica notaEletronica, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
		if (result.hasErrors()) {
			return carregarPagina(notaEletronica);
		}
		
		try {
			if (notaEletronica.isNova()) {				
				NotaEletronica notaEletronicaGravada = this.notaEletronicaService.salvar(notaEletronica);
				
				Beneficio beneficio = new Beneficio(notaEletronicaGravada);
					
				this.beneficioService.salvar(beneficio);
			} else {
				this.notaEletronicaService.salvar(notaEletronica);
			}

		} catch (DadoJaCadastradoException e) {
			result.rejectValue(null, e.getMessage(), e.getMessage());
			return carregarPagina(notaEletronica);
		} catch (DataInvalidaException e) {
			result.rejectValue(null, e.getMessage(), e.getMessage());
			return carregarPagina(notaEletronica);
		}
		
		attributes.addFlashAttribute("mensagem", "Nota Eletrônica gravada com sucesso!");
		return new ModelAndView("redirect:/nota/cadastro");
	}
	
	@GetMapping
	public ModelAndView pesquisar(NotaEletronicaFilter notaEletronicaFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView("nota/consulta");
		modelAndView.addObject("tiposDeSeries", TipoSerie.values());
		
		PageWrapper<NotaEletronica> pageWrapper = new PageWrapper<>(this.notasEletronicas.filtrar(notaEletronicaFilter, pageable), httpServletRequest);
		
		modelAndView.addObject("pagina", pageWrapper);
		
		return modelAndView;
	}
	
	@GetMapping("{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		NotaEletronica notaEletronica = this.notasEletronicas.findByCodigoIn(codigo);
		
		ModelAndView modelAndView = carregarPagina(notaEletronica);
		modelAndView.addObject("notaEletronica", notaEletronica);
		
		return modelAndView;
	}
	
}
