package br.com.apptrechos.torcidapremiada.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InfoController {

	@GetMapping("/info")
	public ModelAndView carregarPagina() {
		ModelAndView modelAndView = new ModelAndView("info");
		return modelAndView;
	}
	
}
