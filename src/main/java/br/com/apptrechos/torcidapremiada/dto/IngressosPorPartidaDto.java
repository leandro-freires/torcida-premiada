package br.com.apptrechos.torcidapremiada.dto;

public class IngressosPorPartidaDto {
	private String diaMesAno;
	private Integer totalDeIngressos;

	public IngressosPorPartidaDto() {

	}

	public IngressosPorPartidaDto(String diaMesAno, Integer totalDeIngressos) {
		this.diaMesAno = diaMesAno;
		this.totalDeIngressos = totalDeIngressos;
	}

	public String getDiaMesAno() {
		return diaMesAno;
	}

	public void setDiaMesAno(String diaMesAno) {
		this.diaMesAno = diaMesAno;
	}

	public Integer getTotalDeIngressos() {
		return totalDeIngressos;
	}

	public void setTotalDeIngressos(Integer totalDeIngressos) {
		this.totalDeIngressos = totalDeIngressos;
	}
}
