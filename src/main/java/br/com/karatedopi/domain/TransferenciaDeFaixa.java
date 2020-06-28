package br.com.karatedopi.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.karatedopi.domain.enums.GraduacaoEnum;

@Entity
public class TransferenciaDeFaixa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private GraduacaoEnum faixaOrigem;

	@Enumerated(EnumType.STRING)
	private GraduacaoEnum faixaDestino;

	private LocalDate dataTransferenciaDeFaixa;

	@ManyToOne
	Perfil perfil;

	public TransferenciaDeFaixa() {
		super();
	}

	public TransferenciaDeFaixa(GraduacaoEnum faixaOrigem, GraduacaoEnum faixaDestino,
			LocalDate dataTransferenciaDeFaixa) {
		this.faixaOrigem = faixaOrigem;
		this.faixaDestino = faixaDestino;
		this.dataTransferenciaDeFaixa = dataTransferenciaDeFaixa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GraduacaoEnum getFaixaOrigem() {
		return faixaOrigem;
	}

	public void setFaixaOrigem(GraduacaoEnum faixaOrigem) {
		this.faixaOrigem = faixaOrigem;
	}

	public GraduacaoEnum getFaixaDestino() {
		return faixaDestino;
	}

	public void setFaixaDestino(GraduacaoEnum faixaDestino) {
		this.faixaDestino = faixaDestino;
	}

	public LocalDate getDataTransferenciaDeFaixa() {
		return dataTransferenciaDeFaixa;
	}

	public void setDataTransferenciaDeFaixa(LocalDate dataTransferenciaDeFaixa) {
		this.dataTransferenciaDeFaixa = dataTransferenciaDeFaixa;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		TransferenciaDeFaixa other = (TransferenciaDeFaixa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
