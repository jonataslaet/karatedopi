package br.com.karatedopi.controllers.dtos;

import java.util.List;
import java.util.stream.Collectors;

import br.com.karatedopi.domain.Localidade;

public class LocalidadeDto {
	
	private Long id;
	private String cidade;
	private String estado;
	private String country;
	
	public LocalidadeDto(Localidade localidade) {
		this.id = localidade.getId();
		this.cidade = localidade.getCidade();
		this.estado = localidade.getUf().toString();
		this.country = localidade.getCountry();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public static List<LocalidadeDto> locaisDto(List<Localidade> locais){
		return locais.stream().map(LocalidadeDto::new).collect(Collectors.toList());
	}

}
