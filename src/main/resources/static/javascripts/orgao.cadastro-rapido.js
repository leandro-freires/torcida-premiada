var TorcidaPremiada = TorcidaPremiada || {};

TorcidaPremiada.OrgaoCadastroRapido = (function() {
	
	function OrgaoCadastroRapido() {
		this.modal = $('#modalCadastroRapidoOrgao');
		this.form = this.modal.find('form');
		this.formGroup = this.form.find('.form-group');
		this.inputNome = $('#nomeOrgao');
		this.containerMensagemErro = $('.js-container-mensagem-erro-cadastro-rapido');
		this.botaoSalvar = this.modal.find('.js-modal-cadastro-rapido-btn-salvar');
		this.url = this.form.attr('action');
	}
	
	OrgaoCadastroRapido.prototype.iniciar = function() {
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
		var nomeOrgao = this.inputNome.val().trim();
		$.ajax({
			url: this.url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ nome: nomeOrgao }),
			error: onError.bind(this),
			success: onSuccess.bind(this)		
		});
	}
	
	function onSuccess(orgao) {
		var comboBoxOrgao = $('#orgao');
		comboBoxOrgao.append('<option value=' + orgao.codigo + '>' + orgao.nome + '</option>');
		comboBoxOrgao.val(orgao.codigo);
		this.modal.modal('hide');
	}
	
	function onError(object) {
		var mensagem = object.responseText;
		this.containerMensagemErro.removeClass('hidden');
		this.containerMensagemErro.html('<i class="fa fa-exclamation-circle"></i><span> ' + mensagem + '</span>');
		this.formGroup.addClass('has-error');
	}
	
	return OrgaoCadastroRapido;
}());

$(function() {
	var orgaoCadastroRapido = new TorcidaPremiada.OrgaoCadastroRapido();
	orgaoCadastroRapido.iniciar();
});