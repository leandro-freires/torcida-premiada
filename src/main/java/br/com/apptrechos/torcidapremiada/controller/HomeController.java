package br.com.apptrechos.torcidapremiada.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.apptrechos.torcidapremiada.repository.BeneficiosEntregues;
import br.com.apptrechos.torcidapremiada.repository.Contribuintes;
import br.com.apptrechos.torcidapremiada.repository.Imoveis;
import br.com.apptrechos.torcidapremiada.repository.NotasEletronicas;

@Controller
public class HomeController {
	
	@Autowired
	private Contribuintes contribuintes;
	
	@Autowired
	private Imoveis imoveis;
	
	@Autowired
	private NotasEletronicas notasEletronicas;
	
	@Autowired
	private BeneficiosEntregues beneficiosEntregues;

	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("valorTotalDeContribuintes", this.contribuintes.totalDeContribuintes());
		modelAndView.addObject("valorTotalDeImoveis", this.imoveis.totalDeImoveis());
		modelAndView.addObject("valorTotalDeNotas", this.notasEletronicas.totalDeNotas());
		modelAndView.addObject("valorTotalDeBeneficiosEntregues", this.beneficiosEntregues.totalDeBeneficiosConcedidos());
		
		return modelAndView;
	}
	
}
