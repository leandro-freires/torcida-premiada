package br.com.apptrechos.torcidapremiada.controller;

import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.apptrechos.torcidapremiada.dto.IngressoEntregueRelatorio;
import br.com.apptrechos.torcidapremiada.dto.PartidaRelatorio;
import br.com.apptrechos.torcidapremiada.repository.Partidas;

@Controller
@RequestMapping("/relatorio")
public class RelatoriosController {
	@Autowired
	private Partidas partidas;

	@GetMapping("/ingressosDistribuidosPorPartida")
	public ModelAndView relatorioIngressosDistribuidosPorPartida(PartidaRelatorio partidaRelatorio) {
		ModelAndView modelAndView = new ModelAndView("relatorios/relatorio-ingressos-distribuidos");
		modelAndView.addObject("partidas", this.partidas.buscarPartidas());
		modelAndView.addObject("partidaRelatorio", partidaRelatorio);
		
		return modelAndView;
	}
	
	@PostMapping("/ingressosDistribuidosPorPartida")
	public ModelAndView gerarRelatorioIngressosDistribuidosPorPartida(@Valid PartidaRelatorio partidaRelatorio, BindingResult result) {
		if (result.hasErrors()) {
			return relatorioIngressosDistribuidosPorPartida(partidaRelatorio);
		}

		Map<String, Object> parametros = new HashMap<>();
		Date dataDaPartida =  Date.from(partidaRelatorio.getPartida().getData().atStartOfDay(ZoneId.systemDefault()).toInstant());
		parametros.put("format", "pdf");
		parametros.put("data_partida", dataDaPartida);
		
		return new ModelAndView("relatorio_ingressos_distribuidos_por_partida", parametros);
	}
	
	@GetMapping("/ingressosEntreguesPeloUsuario")
	public ModelAndView relatorioIngressosEntreguesPeloUsuario(IngressoEntregueRelatorio ingressoEntregueRelatorio) {
		ModelAndView modelAndView = new ModelAndView("relatorios/relatorio-ingresso-entregue-pelo-usuario");
		modelAndView.addObject("partidas", this.partidas.buscarPartidas());
		modelAndView.addObject("partidaRelatorio", ingressoEntregueRelatorio);
		
		return modelAndView;
	}
	
	@PostMapping("/ingressosEntreguesPeloUsuario")
	public ModelAndView gerarRelatorioIngressosEntreguesPeloUsuario(@Valid IngressoEntregueRelatorio ingressoEntregueRelatorio, BindingResult result) {
		if (result.hasErrors()) {
			return relatorioIngressosEntreguesPeloUsuario(ingressoEntregueRelatorio);
		}

		Map<String, Object> parametros = new HashMap<>();
		Date dataDaPartida =  Date.from(ingressoEntregueRelatorio.getPartida().getData().atStartOfDay(ZoneId.systemDefault()).toInstant());
		parametros.put("format", "pdf");
		parametros.put("data_partida", dataDaPartida);
		parametros.put("numero_ingresso", ingressoEntregueRelatorio.getNumeroDoIngresso());
		
		return new ModelAndView("relatorio_ingresso_entregue_pelo_usuario", parametros);
	}
	
}
