TorcidaPremiada = TorcidaPremiada || {};

TorcidaPremiada.PesquisaRapidaContribuinte = (function() {
	
	function PesquisaRapidaContribuinte() {
		this.pesquisaRapidaDeContribuintesModal = $('#pesquisaRapidaDeContribuintes');
		this.url = this.pesquisaRapidaDeContribuintesModal.find('form').attr('action');
		this.cpfOuCnpjContribuinte = $('#cpfcnpj');
		this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-do-contribuinte-btn');
		this.containerDaTabelaDePesquisa = $('#containerDaTabelaPesquisaRapidaDeContribuintes');
		this.htmlDaTabelaDePesquisa = $('#tabelaDeContribuintes').html();
		this.template = Handlebars.compile(this.htmlDaTabelaDePesquisa);
		this.mensagemErro = $('.js-mensagem-de-erro');
	}
	
	PesquisaRapidaContribuinte.prototype.iniciar = function() {
		this.pesquisaRapidaBtn.on('click', onPesquisaRapidaBtnClicado.bind(this));
		this.pesquisaRapidaDeContribuintesModal.on('shown.bs.modal', onModalShow.bind(this));
	}
	
	function onModalShow() {
		this.cpfOuCnpjContribuinte.focus();
	}
	
	function onPesquisaRapidaBtnClicado() {
		$.ajax({
			url: this.url,
			method: 'GET',
			contentType: 'application/json',
			data: {
				cpfOuCnpj: this.cpfOuCnpjContribuinte.val()
			},
			success: onPesquisaConcluida.bind(this),
			error: onErroNaPesquisaRapida.bind(this)
		});
	}
	
	function onPesquisaConcluida(result) {
		this.mensagemErro.addClass('hidden');
		
		this.cpfOuCnpjContribuinte.val('');
		
		var htmlCode = this.template(result);
		this.containerDaTabelaDePesquisa.html(htmlCode);
		
		var tabelaDaPesquisaRapidaDoContribuinte = new TorcidaPremiada.TabelaDaPesquisaRapidaDoContribuinte(this.pesquisaRapidaDeContribuintesModal);
		tabelaDaPesquisaRapidaDoContribuinte.iniciar();
	}
	
	function onErroNaPesquisaRapida() {
		this.mensagemErro.removeClass('hidden');
		this.cpfOuCnpjContribuinte.val('');
	}
	
	return PesquisaRapidaContribuinte;
}());

TorcidaPremiada.TabelaDaPesquisaRapidaDoContribuinte = (function() {
	function TabelaDaPesquisaRapidaDoContribuinte(modal) {
		this.modalDaPesquisaRapidaDoContribuinte = modal;
		this.contribuinte = $('.js-resultado-da-pesquisa-rapida-do-contribuinte');
		this.link = $('.js-link');
	}
	
	TabelaDaPesquisaRapidaDoContribuinte.prototype.iniciar = function() {
		this.contribuinte.on('click', onContribuinteSelecionado.bind(this));
		this.link.on('click', onLinkClicado.bind(this));
	}
	
	function onContribuinteSelecionado(event) {
		this.modalDaPesquisaRapidaDoContribuinte.modal('hide');
		
		var contribuinteSelecionado = $(event.currentTarget);
		
		$('#codigoContribuinte').val(contribuinteSelecionado.data('codigo'));
		$('#nomeContribuinte').val(contribuinteSelecionado.data('nome'));
	}
	
	function onLinkClicado(event) {
		event.preventDefault();
	}
	
	return TabelaDaPesquisaRapidaDoContribuinte;
}());

$(function() {
	var pesquisaRapidaContribuinte = new TorcidaPremiada.PesquisaRapidaContribuinte();
	pesquisaRapidaContribuinte.iniciar();
});