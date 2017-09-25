var TorcidaPremiada = TorcidaPremiada || {};

TorcidaPremiada.ImovelConcederBeneficio = (function() {
	
	function ImovelConcederBeneficio() {
		this.validaBeneficioBtn = $('.js-valida-beneficio-btn');
		this.containerMensagemSucesso = $('.js-container-mensagem-sucesso'); 
	}
	
	ImovelConcederBeneficio.prototype.iniciar = function() {
		this.validaBeneficioBtn.on('click', onBotaoValidaBeneficioClick.bind(this));	
	}
	
	function onBotaoValidaBeneficioClick(event) {
		event.preventDefault();
		var botao = $(event.currentTarget);
		var codigo = botao.data('codigo');
		var url = botao.data('url');
		
		console.log(codigo);
		console.log(url);
		
		$.ajax({
			url: url,
			method: 'POST',
			data: JSON.stringify({codigo: codigo}),
			dataType: 'json',
			contentType: 'application/json',
			success: onSuccess.bind(this)
		});
	}
	
	function onSuccess() {
		var mensagem = 'Benefício concedido com sucesso!';
		this.containerMensagemSucesso.removeClass('hidden');
		this.containerMensagemSucesso.html('<span>' + mensagem + '</span>');
	}
	
	return ImovelConcederBeneficio;
}());

$(function() {
	var imovelConcederBeneficio = new TorcidaPremiada.ImovelConcederBeneficio();
	imovelConcederBeneficio.iniciar();
});