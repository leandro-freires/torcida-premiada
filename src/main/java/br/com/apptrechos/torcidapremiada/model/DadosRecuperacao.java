package br.com.apptrechos.torcidapremiada.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public class DadosRecuperacao {
	@CPF(message = "CPF inválido")
	@NotBlank(message = "Campo obrigatório")
	private String cpf;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}
