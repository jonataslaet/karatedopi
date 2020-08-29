package br.com.karatedopi.controllers.dtos;

import java.util.List;
import java.util.stream.Collectors;

import br.com.karatedopi.domain.Localidade;

public class LocalidadeDto {
	
	private Long id;
	private String rua;
	private String numero;
	private String cep;
	private String bairro;
	
	public LocalidadeDto(Localidade localidade) {
		this.id = localidade.getId();
		this.numero = localidade.getNumero();
		this.cep = localidade.getCep();
		this.bairro = localidade.getBairro();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public static List<LocalidadeDto> locaisDto(List<Localidade> locais){
		return locais.stream().map(LocalidadeDto::new).collect(Collectors.toList());
	}

}
