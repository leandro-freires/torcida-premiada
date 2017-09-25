TorcidaPremiada = TorcidaPremiada || {};

TorcidaPremiada.Multiselecao = (function() {	
	function Multiselecao() {
		this.statusBtn = $('.js-status-btn');
		this.checkboxSeleciona = $('.js-selecao');
		this.checkboxSelecionaTodos = $('.js-selecao-todos');
		this.cancelarBtn = $('.js-cancelar-btn');
	}
	
	Multiselecao.prototype.iniciar = function() {
		this.statusBtn.on('click', onStatusBtnClicado.bind(this));
		this.checkboxSelecionaTodos.on('click', onSelecionaTodosClicado.bind(this));
		this.checkboxSeleciona.on('click', onCheckboxSelecionado.bind(this));
		this.cancelarBtn.on('click', onCancelarBtnClicado.bind(this));
	}
	
	function onStatusBtnClicado(event) {
		var botaoClicado = $(event.currentTarget);
		var status = botaoClicado.data('status');
		var url = botaoClicado.data('url');
		
		var checkboxesSelecionados = this.checkboxSeleciona.filter(':checked');
		
		var codigos = $.map(checkboxesSelecionados, function(c) {
			return $(c).data('codigo');
		});
		
		if (codigos.length > 0) {
			$.ajax({
				url: url,
				method: 'POST',
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify({
					codigos: codigos,
					status: status
				}),
				success: function() {
					window.location.reload();
				}
			});	
		}
		
	}
	
	
	function onSelecionaTodosClicado() {
		var status = this.checkboxSelecionaTodos.prop('checked');
		this.checkboxSeleciona.prop('checked', status);
		ativarDesativarStatusBtn.call(this, status);
	}
	
	function onCheckboxSelecionado() {
		var checkboxChecados = this.checkboxSeleciona.filter(':checked');
		this.checkboxSelecionaTodos.prop('checked', checkboxChecados.length >= this.checkboxSeleciona.length);
		ativarDesativarStatusBtn.call(this, checkboxChecados.length);
	}
	
	function ativarDesativarStatusBtn(valor) {
		valor ? this.statusBtn.removeClass('disabled') : this.statusBtn.addClass('disabled');
	}
	
	function onCancelarBtnClicado(event) {
		event.preventDefault();
		window.history.back();
	}
	
	return Multiselecao;
}());

$(function() {
	var multiselecao = new TorcidaPremiada.Multiselecao();
	multiselecao.iniciar();
});