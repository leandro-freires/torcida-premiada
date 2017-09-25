package br.com.apptrechos.torcidapremiada.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.apptrechos.torcidapremiada.controller.page.PageWrapper;
import br.com.apptrechos.torcidapremiada.dto.IngressosPorPartidaDto;
import br.com.apptrechos.torcidapremiada.model.Beneficio;
import br.com.apptrechos.torcidapremiada.model.Imovel;
import br.com.apptrechos.torcidapremiada.model.Status;
import br.com.apptrechos.torcidapremiada.model.TipoPessoa;
import br.com.apptrechos.torcidapremiada.repository.BeneficiosEntregues;
import br.com.apptrechos.torcidapremiada.repository.BeneficiosPendentes;
import br.com.apptrechos.torcidapremiada.repository.Partidas;
import br.com.apptrechos.torcidapremiada.repository.filters.BeneficioFilter;
import br.com.apptrechos.torcidapremiada.service.BeneficioService;

@Controller
@RequestMapping("/beneficio")
public class BeneficioController {
	
	@Autowired
	private BeneficiosPendentes beneficiosPendentes;
	
	@Autowired
	private Partidas partidas;
	
	@Autowired
	private BeneficiosEntregues beneficiosEntregues;
	
	@Autowired
	private BeneficioService beneficioService;
	
	@GetMapping("/pendente")
	public ModelAndView pesquisarPendentes(BeneficioFilter beneficioFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView("beneficio/consulta-pendentes");
		
		PageWrapper<Beneficio> pageWrapper = new PageWrapper<>(this.beneficiosPendentes.filtrar(beneficioFilter, pageable), httpServletRequest);
		
		modelAndView.addObject("pagina", pageWrapper);
		modelAndView.addObject("tiposPessoas", TipoPessoa.values());
		
		return modelAndView;
	}
	
	@GetMapping("/entregue")
	public ModelAndView pesquisarEntregues(BeneficioFilter beneficioFilter, BindingResult result, @PageableDefault(size = 20) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView("beneficio/consulta-entregues");
		
		PageWrapper<Beneficio> pageWrapper = new PageWrapper<>(this.beneficiosEntregues.filtrar(beneficioFilter, pageable), httpServletRequest);
		
		modelAndView.addObject("pagina", pageWrapper);
		modelAndView.addObject("tiposPessoas", TipoPessoa.values());
		
		return modelAndView;
	}

	@GetMapping("/liberacao/{codigo}")
	public ModelAndView novoBeneficio(@PathVariable("codigo") Imovel imovel, RedirectAttributes attributes) {	
		Beneficio beneficio = new Beneficio(imovel);
			
		this.beneficioService.salvar(beneficio);
		
		attributes.addFlashAttribute("mensagem", "Benefício concedido com sucesso!");
		return new ModelAndView("redirect:/beneficio/pendente");
	}
	
	@GetMapping("/ingresso/{codigo}")
	public ModelAndView carregarPagina(@PathVariable("codigo") Beneficio beneficio) {
		ModelAndView modelAndView = new ModelAndView("ingresso/cadastro");
		modelAndView.addObject("beneficio", beneficio);
		modelAndView.addObject("partidas", this.partidas.buscarPartidas());
		
		return modelAndView;
	}
	
	@PostMapping("/ingresso/cadastrar")
	public ModelAndView salvar(Beneficio beneficio, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
		if (StringUtils.isEmpty(beneficio.getNumeroIngressoA()) || StringUtils.isEmpty(beneficio.getNumeroIngressoB())) {	
			result.rejectValue("numeroIngressoA", "", "O número do ingresso 1 é obrigatório");
			result.rejectValue("numeroIngressoB", "", "O número do ingresso 2 é obrigatório");
			return carregarPagina(beneficio);
		}
		
		if (beneficio.getNumeroIngressoA().equals(beneficio.getNumeroIngressoB())) {	
			result.rejectValue("numeroIngressoA", "", "O número do ingresso 1 é único");
			result.rejectValue("numeroIngressoB", "", "O número do ingresso 2 é único");
			return carregarPagina(beneficio);
		}
		
		if (beneficio.getPartida() == null) {
			result.rejectValue("partida", "", "A partida é obrigatória");
			return carregarPagina(beneficio);
		}
		
		if (beneficio.getNotaEletronica() != null) {
			Optional<Beneficio> beneficioConcedido = this.beneficiosPendentes.buscarBeneficioConcedido(beneficio.getNotaEletronica());
			
			if (beneficioConcedido.isPresent()) {
				result.rejectValue(null, "Os ingressos para esse jogo já foram cadastrados", "Os ingressos para esse jogo já foram cadastrados");
				return carregarPagina(beneficio);
			}
			
			List<Beneficio> quantidade = this.beneficiosPendentes.buscarQuantidadeDeBeneficiosConcedidoPorContribuinte(beneficio.getNotaEletronica().getContribuinte(), beneficio.getPartida());
			System.out.println("Quantidade: " + quantidade.size());
			if (quantidade.size() >= 3) {
				result.rejectValue(null, "O limite de ingressos foi atingido", "O limite de ingressos foi atingido");
				return carregarPagina(beneficio);
			}	
		} else {
			Optional<Beneficio> beneficioConcedido = this.beneficiosPendentes.buscarBeneficioConcedido(beneficio.getImovel(), beneficio.getPartida());
			
			if (beneficioConcedido.isPresent()) {
				result.rejectValue(null, "Os ingressos para esse jogo já foram cadastrados", "Os ingressos para esse jogo já foram cadastrados");
				return carregarPagina(beneficio);
			}
			
			List<Beneficio> quantidade = this.beneficiosPendentes.buscarQuantidadeDeBeneficiosConcedidoPorContribuinte(beneficio.getContribuinte(), beneficio.getPartida());
			
			if (quantidade.size() >= 3) {
				result.rejectValue(null, "O limite de ingressos foi atingido", "O limite de ingressos foi atingido");
				return carregarPagina(beneficio);
			}
		}
		
		beneficio.setStatus(Status.ENTREGUE);
		beneficio.setCpfUsuario(request.getUserPrincipal().getName());
		beneficio.setHoraDataDaEntrega(LocalDateTime.now());
	
		this.beneficioService.salvar(beneficio);
		
		attributes.addFlashAttribute("mensagem", "Ingressos gravados com sucesso!");
		return new ModelAndView("redirect:/beneficio/entregue");
	}
	
	@GetMapping("/totalDeIngressosPorPartidaNoMes")
	public @ResponseBody List<IngressosPorPartidaDto> listarTotalDeVendasPorMes() {
		return this.beneficiosEntregues.totalDeIngressosPorPartidaNoMes();
	}
		
}
