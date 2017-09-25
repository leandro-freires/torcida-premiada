package br.com.apptrechos.torcidapremiada.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "beneficio")
@DynamicUpdate
public class Beneficio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(name = "codigo_partida")
	private Partida partida;
	
	@OneToOne
	@JoinColumn(name = "codigo_nota")
	private NotaEletronica notaEletronica;
	
	@ManyToOne
	@JoinColumn(name = "codigo_imovel")
	private Imovel imovel;
	
	@Column(name = "numero_ingresso_a")
	private String numeroIngressoA;
	
	@Column(name = "numero_ingresso_b")
	private String numeroIngressoB;
	
	@Column(name = "cpf_usuario")
	private String cpfUsuario;
	
	@Column(name = "data_hora_entrega")
	private LocalDateTime horaDataDaEntrega;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "codigo_contribuinte")
	private Contribuinte contribuinte;
	
	public Beneficio() {
		
	}
	
	public Beneficio(NotaEletronica notaEletronica) {
		this.notaEletronica = notaEletronica;
	}

	public Beneficio(Imovel imovel) {
		this.imovel = imovel;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public String getCpfUsuario() {
		return cpfUsuario;
	}

	public void setCpfUsuario(String cpfUsuario) {
		this.cpfUsuario = cpfUsuario;
	}

	public NotaEletronica getNotaEletronica() {
		return notaEletronica;
	}

	public void setNotaEletronica(NotaEletronica notaEletronica) {
		this.notaEletronica = notaEletronica;
	}

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}
	
	public String getNumeroIngressoA() {
		return numeroIngressoA;
	}

	public void setNumeroIngressoA(String numeroIngressoA) {
		this.numeroIngressoA = numeroIngressoA;
	}

	public String getNumeroIngressoB() {
		return numeroIngressoB;
	}

	public void setNumeroIngressoB(String numeroIngressoB) {
		this.numeroIngressoB = numeroIngressoB;
	}

	public LocalDateTime getHoraDataDaEntrega() {
		return horaDataDaEntrega;
	}

	public void setHoraDataDaEntrega(LocalDateTime horaDataDaEntrega) {
		this.horaDataDaEntrega = horaDataDaEntrega;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Contribuinte getContribuinte() {
		return contribuinte;
	}

	public void setContribuinte(Contribuinte contribuinte) {
		this.contribuinte = contribuinte;
	}

	public boolean isEntregue() {
		return this.status.equals(Status.ENTREGUE);
	}
	
	public boolean isNotaEletronicaDiferenteDeNull() {
		return this.notaEletronica != null;
	}
	
	public boolean isIngressosIgualNull() {
		return this.numeroIngressoA == null && this.numeroIngressoB == null;
	}
	
	public boolean isNovo() {
		return this.codigo == null;
	}
	
	@PrePersist
	private void prePersist() {
		if (isNotaEletronicaDiferenteDeNull()) {
			this.contribuinte = this.notaEletronica.getContribuinte();
		} else {
			this.contribuinte = this.imovel.getContribuinte();
		}	
	}
	
	@PreUpdate
	private void preUpdate() {
		this.cpfUsuario = removerFormatacao(this.cpfUsuario);
	}
	
	public void postLoad() {
		this.cpfUsuario = formatar(this.cpfUsuario);
	}

	private String formatar(String cpf) {
		return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
	}

	private String removerFormatacao(String cpf) {
		return cpf.replaceAll("\\.|-", "");
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
		Beneficio other = (Beneficio) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
