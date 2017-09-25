var TorcidaPremiada = TorcidaPremiada || {};

TorcidaPremiada.TimeAdversarioCadastroRapido = (function() {
	
	function TimeAdversarioCadastroRapido() {
		this.modal = $('#modalCadastroRapidoEquipeAdversaria');
		this.form = this.modal.find('form');
		this.formGroup = this.form.find('.form-group');
		this.inputNome = $('#nomeTimeAdversario');
		this.containerMensagemErro = $('.js-container-mensagem-erro-cadastro-rapido');
		this.botaoSalvar = this.modal.find('.js-modal-cadastro-rapido-btn-salvar');
		this.url = this.form.attr('action');
	}
	
	TimeAdversarioCadastroRapido.prototype.iniciar = function() {
		this.form.on('submit', function(event) {event.preventDefault()});
		this.modal.on('shown.bs.modal', onModalShow.bind(this));
		this.modal.on('hide.bs.modal', onModalClose.bind(this));
		this.botaoSalvar.on('click', onBotaoSalvarClick.bind(this));
		
	}
	
	function onModalShow() {
		this.inputNome.focus();
	}
	
	function onModalClose() {
		this.inputNome.val('');
		this.containerMensagemErro.addClass('hidden');
		this.formGroup.removeClass('has-error');
	}
	
	function onBotaoSalvarClick() {
		var nomeEquipe = this.inputNome.val().trim();
		
		$.ajax({
			url: this.url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ nome: nomeEquipe }),
			error: onError.bind(this),
			success: onSuccess.bind(this)		
		});
	}
	
	function onSuccess(equipe) {
		var comboBoxOrgao = $('#equipeAdversaria');
		comboBoxOrgao.append('<option value=' + equipe.codigo + '>' + equipe.nome + '</option>');
		comboBoxOrgao.val(equipe.codigo);
		this.modal.modal('hide');
	}
	
	function onError(object) {
		var mensagem = object.responseText;
		this.containerMensagemErro.removeClass('hidden');
		this.containerMensagemErro.html('<i class="fa fa-exclamation-circle"></i><span> ' + mensagem + '</span>');
		this.formGroup.addClass('has-error');
	}
	
	return TimeAdversarioCadastroRapido;
}());

$(function() {
	var timeAdversarioCadastroRapido = new TorcidaPremiada.TimeAdversarioCadastroRapido();
	timeAdversarioCadastroRapido.iniciar();
});