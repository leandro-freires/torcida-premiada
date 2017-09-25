package br.com.apptrechos.torcidapremiada.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.apptrechos.torcidapremiada.controller.page.PageWrapper;
import br.com.apptrechos.torcidapremiada.model.Contribuinte;
import br.com.apptrechos.torcidapremiada.model.TipoPessoa;
import br.com.apptrechos.torcidapremiada.repository.Contribuintes;
import br.com.apptrechos.torcidapremiada.repository.filters.ContribuinteFilter;
import br.com.apptrechos.torcidapremiada.service.ContribuinteService;
import br.com.apptrechos.torcidapremiada.service.exception.CpfOuCnpjInvalidoException;
import br.com.apptrechos.torcidapremiada.service.exception.DadoJaCadastradoException;
import br.com.apptrechos.torcidapremiada.util.ValidadorDeCnpj;
import br.com.apptrechos.torcidapremiada.util.ValidadorDeCpf;

@Controller
@RequestMapping("/contribuinte")
public class ContribuinteController {
	
	@Autowired
	private Contribuintes contribuintes;
	
	@Autowired
	private ContribuinteService contribuinteService;
	
	@GetMapping("/cadastro")
	public ModelAndView carregarPagina(Contribuinte contribuinte) {
		ModelAndView modelAndView = new ModelAndView("contribuinte/cadastro");
		modelAndView.addObject("tiposPessoas", TipoPessoa.values());
		
		return modelAndView;
	}
	
	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView salvar(@Valid Contribuinte contribuinte, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return carregarPagina(contribuinte);
		}
		
		try {
			this.contribuinteService.salvar(contribuinte);
		} catch (DadoJaCadastradoException e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return carregarPagina(contribuinte);
		}
		
		attributes.addFlashAttribute("mensagem", "Contribuinte cadastrado com sucesso!");
		return new ModelAndView("redirect:/contribuinte/cadastro");
	}
	
	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Contribuinte pesquisar(String cpfOuCnpj) {
		String cpfOuCnpjSemFormatacao = cpfOuCnpj.replaceAll("\\.|-|/", "");
		
		if (cpfOuCnpjSemFormatacao.length() < 14) {
			if (!ValidadorDeCpf.isValido(cpfOuCnpjSemFormatacao)) {
				throw new CpfOuCnpjInvalidoException("CPF Inválido");
			}
		} else {
			if (!ValidadorDeCnpj.isValido(cpfOuCnpjSemFormatacao)) {
				throw new CpfOuCnpjInvalidoException("CNPJ Inválido");
			}
		}
		
		Optional<Contribuinte> contribuinte = this.contribuintes.findByCpfOuCnpj(cpfOuCnpjSemFormatacao);
		
		return contribuinte.get();
	}

	@ExceptionHandler(CpfOuCnpjInvalidoException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(CpfOuCnpjInvalidoException e) {
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping
	public ModelAndView pesquisar(ContribuinteFilter contribuinteFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView("contribuinte/consulta");
		
		PageWrapper<Contribuinte> pageWrapper = new PageWrapper<>(this.contribuintes.filtrar(contribuinteFilter, pageable), httpServletRequest);
		
		modelAndView.addObject("tiposPessoas", TipoPessoa.values());
		modelAndView.addObject("pagina", pageWrapper);
		
		return modelAndView;
	}
	
	@GetMapping("{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Contribuinte contribuinte = this.contribuintes.findByCodigoIn(codigo);
		
		ModelAndView modelAndView = carregarPagina(contribuinte);
		modelAndView.addObject("contribuinte", contribuinte);
		
		return modelAndView;
	}
	
}
