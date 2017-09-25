package br.com.apptrechos.torcidapremiada.service.exception;

public class CpfOuCnpjInvalidoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CpfOuCnpjInvalidoException(String msg) {
		super(msg);
	}

	public CpfOuCnpjInvalidoException() {
	}
	
}
