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

@Entity
@Table(name = "partida")
public class Partida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "equipe_anapolina")
	@NotNull(message = "Equipe Anapolina é obrigatorio")
	private EquipesAnapolinas equipeAnapolina;
	
	@ManyToOne
	@JoinColumn(name = "equipe_adversaria")
	@NotNull(message = "Equipe Adversária é obrigatório")
	private Equipe equipeAdversaria;
	
	@Column(name = "data")
	@NotNull(message = "A data é obrigatória")
	private LocalDate data;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Campeonato é obrigatorio")
	private Campeonato campeonato;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public EquipesAnapolinas getEquipeAnapolina() {
		return equipeAnapolina;
	}

	public void setEquipeAnapolina(EquipesAnapolinas equipeAnapolina) {
		this.equipeAnapolina = equipeAnapolina;
	}

	public Equipe getEquipeAdversaria() {
		return equipeAdversaria;
	}

	public void setEquipeAdversaria(Equipe equipeAdversaria) {
		this.equipeAdversaria = equipeAdversaria;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
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
		Partida other = (Partida) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
