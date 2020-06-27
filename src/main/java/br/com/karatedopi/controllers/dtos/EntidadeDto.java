package br.com.karatedopi.controllers.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.karatedopi.domain.Entidade;

public class EntidadeDto {

	private Long id;
	private String nome;
	private String sigla;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFundacao;
	
	public EntidadeDto(Entidade entidade) {
		this.id = entidade.getId();
		this.nome = entidade.getNome();
		this.sigla = entidade.getSigla();
		this.dataFundacao = entidade.getDataFundacao();
	}
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getSigla() {
		return sigla;
	}



	public void setSigla(String sigla) {
		this.sigla = sigla;
	}



	public LocalDate getDataFundacao() {
		return dataFundacao;
	}



	public void setDataFundacao(LocalDate dataFundacao) {
		this.dataFundacao = dataFundacao;
	}



	public static List<EntidadeDto> entidadesDto(List<Entidade> entidades){
		return entidades.stream().map(EntidadeDto::new).collect(Collectors.toList());
	}
}
