TorcidaPremiada = TorcidaPremiada || {};

TorcidaPremiada.ModalCadastroDeIngressos = (function() {
	
	function ModalCadastroDeIngressos() {
		this.url = $('#tabelaDeBeneficios').data('url');
		this.beneficioBtn = $('.js-beneficio-btn');
		this.containerModalCadastroDeIngressos = $('#containerModalCadastroDeIngressos');
		this.modalCadastroDeIngresso = $('#modalCadastroDeIngressos');
//		this.inputIngressoUm = $('#ingressoUm');
//		this.inputIngressoDois = $('#ingressoDois');
	}
	
	ModalCadastroDeIngressos.prototype.iniciar = function() {
		this.beneficioBtn.on('click', onBeneficioBtnClicado.bind(this));
//		this.modalCadastroDeIngresso.on('shown.bs.modal', onModalShow.bind(this));
//		this.modalCadastroDeIngresso.on('hide.bs.modal', onModalClose.bind(this));
	}
	
	function onBeneficioBtnClicado() {
		var codigo = this.beneficioBtn.data('codigo');
		
		$.ajax({
			url: this.url + '/' + codigo,
			dataType: 'html',
			success: onModalCadastroDeIngressosShow.bind(this)
		});
		
		function onModalCadastroDeIngressosShow(data) {
			console.log(data);
			this.containerModalCadastroDeIngressos.html(data);
			this.modalCadastroDeIngresso.modal('show');
			
		}
		
//		function onModalShow() {
//			this.inputIngressoUm.focus();
//		}
//		
//		function onModalClose() {
//			this.inputIngressoUm.val('');
//			this.inputIngressoDois.val('');
//		}
	}
	
	return ModalCadastroDeIngressos;
}());

$(function() {
	var modalCadastroDeIngressos = new TorcidaPremiada.ModalCadastroDeIngressos();
	modalCadastroDeIngressos.iniciar();
});