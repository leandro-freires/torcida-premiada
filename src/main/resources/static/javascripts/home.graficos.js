var TorcidaPremiada = TorcidaPremiada || {};

TorcidaPremiada.GraficoIngressosPorPartida = (function() {
	function GraficoIngressosPorPartida() {
		this.ctx = $('#graficoIngressosPorPartida')[0].getContext('2d');
	}
	
	GraficoIngressosPorPartida.prototype.iniciar = function() {
		$.ajax({
			url: 'beneficio/totalDeIngressosPorPartidaNoMes',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(totalDeIngressosPorPartidaNoMes) {
		var partidas = [];
		var valores = [];
		
		totalDeIngressosPorPartidaNoMes.forEach(function(obj) {
			partidas.unshift(obj.diaMesAno);
			valores.unshift(obj.totalDeIngressos);
		});
		
		var myLineChart = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels: partidas,
		    	datasets: [{
		    		label: 'Ingressos por partida',
		    		backgroundColor: "rgba(26,179,148,0.5)",
		    		pointBorderColor: "rgba(26,179,148,1)",
		    		pointBackgroundColor: "#FFF",
		    		data: valores
		    	}]
		    }
		});
	}
	
	return GraficoIngressosPorPartida;
}());

$(function() {
	var graficoIngressosPorPartida = new TorcidaPremiada.GraficoIngressosPorPartida();
	graficoIngressosPorPartida.iniciar();
});