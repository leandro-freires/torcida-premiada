package br.com.apptrechos.torcidapremiada.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.apptrechos.torcidapremiada.controller.page.PageWrapper;
import br.com.apptrechos.torcidapremiada.model.Beneficio;
import br.com.apptrechos.torcidapremiada.model.CondicaoDeResidente;
import br.com.apptrechos.torcidapremiada.model.Imovel;
import br.com.apptrechos.torcidapremiada.model.Situacao;
import br.com.apptrechos.torcidapremiada.model.TipoPessoa;
import br.com.apptrechos.torcidapremiada.repository.Imoveis;
import br.com.apptrechos.torcidapremiada.repository.filters.ImovelFilter;
import br.com.apptrechos.torcidapremiada.service.BeneficioService;
import br.com.apptrechos.torcidapremiada.service.ImovelService;
import br.com.apptrechos.torcidapremiada.service.exception.DadoJaCadastradoException;

@Service
@RequestMapping("/imovel")
public class ImovelController {
	
	@Autowired
	private Imoveis imoveis;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private BeneficioService beneficioService;
	
	@GetMapping("/cadastro")
	public ModelAndView carregarPagina(Imovel imovel) {
		ModelAndView modelAndView = new ModelAndView("imovel/cadastro");
		modelAndView.addObject("condicoesDeResidencia", CondicaoDeResidente.values());
		modelAndView.addObject("situacoes", Situacao.values());
		modelAndView.addObject("tiposPessoas", TipoPessoa.values());
		
		return modelAndView;
	}
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Imovel imovel, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
		if (result.hasErrors()) {
			return carregarPagina(imovel);
		}
		
		try {
			if (imovel.isNovo()) {		
				Imovel imovelGravado = this.imovelService.salvar(imovel);
				
				if(!imovel.getSituacao().equals(Situacao.NEGATIVO)) {
					attributes.addFlashAttribute("mensagem", "Imóvel gravado com sucesso, mas o benefício não foi concedido porque a certidão do imóvel está diferente de Negativo!");
					return new ModelAndView("redirect:/imovel/cadastro");
				}
				
				Beneficio beneficio = new Beneficio(imovelGravado);
					
				this.beneficioService.salvar(beneficio);	
			} else {
				this.imovelService.salvar(imovel);
			}
		} catch (DadoJaCadastradoException e) {
			result.rejectValue(null, e.getMessage(), e.getMessage());
			return carregarPagina(imovel);
		}
		
		attributes.addFlashAttribute("mensagem", "Imóvel gravado com sucesso!");
		return new ModelAndView("redirect:/imovel/cadastro");
	}
	
	@GetMapping
	public ModelAndView pesquisar(ImovelFilter imovelFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView("imovel/consulta");
		modelAndView.addObject("condicoesDeResidencia", CondicaoDeResidente.values());
		modelAndView.addObject("situacoes", Situacao.values());
		
		PageWrapper<Imovel> pageWrapper = new PageWrapper<>(this.imoveis.filtrar(imovelFilter, pageable), httpServletRequest);
		
		modelAndView.addObject("pagina", pageWrapper);
		
		return modelAndView;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Imovel imovel = this.imoveis.findByCodigoIn(codigo);
		
		ModelAndView modelAndView = carregarPagina(imovel);
		modelAndView.addObject("imovel", imovel);
		
		return modelAndView;
	}
	
}
