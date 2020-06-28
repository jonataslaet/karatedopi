package br.com.karatedopi.controllers.cadastros;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.karatedopi.domain.enums.GraduacaoEnum;

public class CadastroTransferenciaDeFaixa {
	private GraduacaoEnum faixaOrigem;
	private GraduacaoEnum faixaDestino;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataTransferenciaDeFaixa;
	
	public CadastroTransferenciaDeFaixa() {
		
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
	
}
