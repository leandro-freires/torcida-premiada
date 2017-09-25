package br.com.apptrechos.torcidapremiada.service.exception;

public class SenhaObrigatoriaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SenhaObrigatoriaException(String msg) {
		super(msg);
	}
}
