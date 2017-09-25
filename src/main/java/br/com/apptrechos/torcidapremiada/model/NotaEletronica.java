package br.com.apptrechos.torcidapremiada.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "nota_fiscal")
@DynamicUpdate
public class NotaEletronica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_serie")
	@NotNull(message = "Tipo de Série é obrigatório")
	private TipoSerie tipoDeSerie;
	
	@Column(name = "data_emissao")
	@NotNull(message = "Data da Emissão é obrigatório")
	private LocalDate dataEmissao;
	
	@Column(name = "inscricao_prestador")
	@NotBlank(message = "Inscrição do Prestador é obrigatório")
	private String inscricaoPrestador;
	
	@NotNull(message = "Número da Nota Fiscal é obrigatório")
	private Integer numero;
	
	@NotBlank(message = "Código de Verificação é obrigatório")
	@Column(name = "codigo_verificacao")
	private String codigoVerificacao;
	
	@ManyToOne
	@JoinColumn(name = "codigo_contribuinte")
	@NotNull(message = "Tomador é obrigatório")
	private Contribuinte contribuinte;
	
	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public TipoSerie getTipoDeSerie() {
		return tipoDeSerie;
	}
	
	public void setTipoDeSerie(TipoSerie tipoDeSerie) {
		this.tipoDeSerie = tipoDeSerie;
	}
	
	public LocalDate getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getInscricaoPrestador() {
		return inscricaoPrestador;
	}
	
	public void setInscricaoPrestador(String inscricaoPrestador) {
		this.inscricaoPrestador = inscricaoPrestador.toUpperCase();
	}
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public String getCodigoVerificacao() {
		return codigoVerificacao;
	}
	
	public void setCodigoVerificacao(String codigoVerificacao) {
		this.codigoVerificacao = codigoVerificacao.toUpperCase();
	}
	
	public Contribuinte getContribuinte() {
		return contribuinte;
	}
	
	public void setContribuinte(Contribuinte contribuinte) {
		this.contribuinte = contribuinte;
	}
	
	public boolean isNova() {
		return this.codigo == null;
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
		NotaEletronica other = (NotaEletronica) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
