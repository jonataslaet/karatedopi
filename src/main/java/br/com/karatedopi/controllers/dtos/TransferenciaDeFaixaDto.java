package br.com.karatedopi.controllers.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.karatedopi.domain.TransferenciaDeFaixa;
import br.com.karatedopi.domain.enums.GraduacaoEnum;

public class TransferenciaDeFaixaDto {
	
	private Long id;
	private GraduacaoEnum faixaOrigem;
	private GraduacaoEnum faixaDestino;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataTransferenciaDeFaixa;
	
	public TransferenciaDeFaixaDto(TransferenciaDeFaixa transfer) {
		this.id = transfer.getId();
		this.faixaOrigem = transfer.getFaixaOrigem();
		this.faixaDestino = transfer.getFaixaDestino();
		this.dataTransferenciaDeFaixa = transfer.getDataTransferenciaDeFaixa();
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

	public static List<TransferenciaDeFaixaDto> transferenciaDeFaixaDto(List<TransferenciaDeFaixa> transfers){
		return transfers.stream().map(TransferenciaDeFaixaDto::new).collect(Collectors.toList());
	}

}
