var TorcidaPremiada = TorcidaPremiada || {};

TorcidaPremiada.RecuperacaoDeSenha = (function() {
	
	function RecuperacaoDeSenha() {
		this.form = $('form');
		this.formGroup = this.form.find('.form-group');
		this.inputCpf = $('#cpf');
		this.containerMensagemErro = $('.alert-danger');
		this.containerMensagemSucesso = $('.alert-success');
		this.mensagemSucesso = $('#mensagemSucesso');
		this.mensagemErro = $('#mensagemErro');
		this.botaoEnviar = $('.js-enviar-btn');
		this.url = this.form.attr('action');
//		this.textoBtn = $('#textoBtn');
//		this.imgLoading = $('.js-img-loading');
		this.preloader = $('#loading');
	}
	
	RecuperacaoDeSenha.prototype.iniciar = function() {
		this.form.on('submit', function(event) {event.preventDefault()});
		this.botaoEnviar.on('click', onBotaoEnviarClick.bind(this));
		this.preloader.hide();
		
	}
	
	function onBotaoEnviarClick() {
		var cpf = this.inputCpf.val().trim();
		iniciarRequisicao.call(this);
		
		$.ajax({
			url: this.url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ cpf: cpf }),
			error: onError.bind(this),
			success: onSuccess.bind(this)		
		});
	}
	
	function onSuccess(mensagem) {
		this.containerMensagemSucesso.removeClass('hidden');
		this.mensagemSucesso.html('<i class="fa fa-check-circle"></i><span> ' + mensagem + '</span>');
		this.containerMensagemErro.addClass('hidden');
		this.inputCpf.val('');
		finalizarRequisicao.call(this);
	}
	
	function onError(object) {
		var mensagem = object.responseText;
		this.containerMensagemErro.removeClass('hidden');
		this.mensagemErro.html('<i class="fa fa-exclamation-circle"></i><span> ' + mensagem + '</span>');
		this.containerMensagemSucesso.addClass('hidden');
		this.inputCpf.val('');
		finalizarRequisicao.call(this);
	}
	
	function iniciarRequisicao() {
		this.preloader.show();
		this.inputCpf.attr('readonly', true);
		this.botaoEnviar.attr('disabled', true);
		
	}
	
	function finalizarRequisicao() {
		this.preloader.hide();
		this.inputCpf.removeAttr('readonly');
		this.botaoEnviar.removeAttr('disabled');	
	}
	
	return RecuperacaoDeSenha;
}());

$(function() {
	var recuperacaoDeSenha = new TorcidaPremiada.RecuperacaoDeSenha();
	recuperacaoDeSenha.iniciar();
});