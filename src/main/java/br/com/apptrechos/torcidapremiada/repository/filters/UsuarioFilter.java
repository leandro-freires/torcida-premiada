package br.com.apptrechos.torcidapremiada.repository.filters;

import java.util.List;

import br.com.apptrechos.torcidapremiada.model.Grupo;
import br.com.apptrechos.torcidapremiada.model.Orgao;

public class UsuarioFilter {
	private String cpf;
	private String nome;
	private Orgao orgao;
	private List<Grupo> grupos;
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Orgao getOrgao() {
		return orgao;
	}
	
	public void setOrgao(Orgao orgao) {
		this.orgao = orgao;
	}
	
	public List<Grupo> getGrupos() {
		return grupos;
	}
	
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
}
