var TorcidaPremiada = TorcidaPremiada || {};

TorcidaPremiada.Security = (function() {
	function Security() {
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.enable = function() {
		$(document).ajaxSend(function(event, jqxhr, settings) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
}());

TorcidaPremiada.MaskIntegerNumber = (function() {
	function MaskIntegerNumber() {
		this.plain = $('.tp-js-plain');
	}
	
	MaskIntegerNumber.prototype.enable = function() {
		this.plain.maskNumber({integer: true, thousands: '.'});
	}
	
	return MaskIntegerNumber;
}());

TorcidaPremiada.MaskCpf = (function() {
	function MaskCpf() {
		this.inputCpf = $('.input-cpf');
	}
	
	MaskCpf.prototype.enable = function() {
		this.inputCpf.mask('000.000.000-00', {reverse: true});
	}
	
	return MaskCpf;	
}());

TorcidaPremiada.MaskDate = (function() {
	function MaskDate() {
		this.inputDate = $('.js-date');
	}
	
	MaskDate.prototype.enable = function() {
		this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation: "bottom",
			language: "pt-BR",
			autoclose: "true"
		});
	}
	
	return MaskDate;
}());

TorcidaPremiada.MaskPhoneNumber = (function() {
	function MaskPhoneNumber() {
		this.inputPhoneNumber = $('.js-phone-number');
	}
	
	MaskPhoneNumber.prototype.enable = function() {
		var maskBehavior = function (val) {
			return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		};
			
		var options = {
			onKeyPress: function(val, e, field, options) {
				field.mask(maskBehavior.apply({}, arguments), options);
			}
		};
			
		this.inputPhoneNumber.mask(maskBehavior, options);
	}
	
	return MaskPhoneNumber;
}());

TorcidaPremiada.MaskImobiliario = (function() {
	function MaskImobiliario() {
		this.inputImobiliario = $('.input-imobiliario');
	}
	
	MaskImobiliario.prototype.enable = function() {
		this.inputImobiliario.mask('000.000.0000.000', {reverse: true});
	}
	
	return MaskImobiliario;	
}());

$(function() {
	var security = new TorcidaPremiada.Security();
	security.enable();
	
	var maskIntegerNumber = new TorcidaPremiada.MaskIntegerNumber();
	maskIntegerNumber.enable();
	
	var maskCpf = new TorcidaPremiada.MaskCpf();
	maskCpf.enable();
	
	var maskImobiliario = new TorcidaPremiada.MaskImobiliario();
	maskImobiliario.enable();
	
	var maskDate = new TorcidaPremiada.MaskDate();
	maskDate.enable();
	
	var maskPhoneNumber = new TorcidaPremiada.MaskPhoneNumber();
	maskPhoneNumber.enable();
});