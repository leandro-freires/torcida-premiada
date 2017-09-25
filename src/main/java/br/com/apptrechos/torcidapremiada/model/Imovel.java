package br.com.apptrechos.torcidapremiada.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "imovel")
@DynamicUpdate
public class Imovel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(name = "inscricao_imobiliaria")
	@NotBlank(message = "Inscrição Imobiliária é obrigatório")
	private String inscricaoImobiliaria;
	
	@ManyToOne
	@JoinColumn(name = "codigo_contribuinte")
	@NotNull(message = "Contribuinte é obrigatório")
	private Contribuinte contribuinte;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "condicao_residente")
	@NotNull(message = "Condição de Residente é obrigatório")
	private CondicaoDeResidente condicaoDeResidente;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Situação é obrigatório")
	private Situacao situacao;
	
	private boolean status;
	
	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public String getInscricaoImobiliaria() {
		return inscricaoImobiliaria;
	}
	
	public void setInscricaoImobiliaria(String inscricaoImobiliaria) {
		this.inscricaoImobiliaria = inscricaoImobiliaria;
	}
	
	public Contribuinte getContribuinte() {
		return contribuinte;
	}
	
	public void setContribuinte(Contribuinte contribuinte) {
		this.contribuinte = contribuinte;
	}
	
	public CondicaoDeResidente getCondicaoDeResidente() {
		return condicaoDeResidente;
	}
	
	public void setCondicaoDeResidente(CondicaoDeResidente condicaoDeResidente) {
		this.condicaoDeResidente = condicaoDeResidente;
	}
	
	public Situacao getSituacao() {
		return situacao;
	}
	
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public boolean isNovo() {
		return this.codigo == null;
	}
	
	public boolean isLiberarBeneficio() {
		return (!this.situacao.equals(Situacao.POSITIVO) && !this.situacao.equals(Situacao.POSITIVO_EFEITO_NEGATIVO)) && this.status;
	}
	
	@PreUpdate
	private void preUpdate() {
		this.inscricaoImobiliaria = removerFormatacao(this.inscricaoImobiliaria);
	}
	
	@PrePersist
	private void prePersist() {
		this.inscricaoImobiliaria = removerFormatacao(this.inscricaoImobiliaria);
	}
	
	@PostLoad
	private void postLoad() {
		this.inscricaoImobiliaria = formatar(this.inscricaoImobiliaria);
	}

	private String formatar(String inscricaoImobiliaria) {
		return inscricaoImobiliaria.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3.");
	}

	private String removerFormatacao(String inscricaoImobiliaria) {
		return inscricaoImobiliaria.replaceAll("\\.", "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Imovel other = (Imovel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
