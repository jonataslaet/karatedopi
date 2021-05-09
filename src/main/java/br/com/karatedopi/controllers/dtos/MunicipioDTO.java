package br.com.karatedopi.controllers.dtos;

import java.util.List;
import java.util.stream.Collectors;

import br.com.karatedopi.domain.Municipio;

public class MunicipioDTO {
	private Long id;
	private String nome;

	public MunicipioDTO(Municipio municipio) {
		this.id = municipio.getId();
		this.nome = municipio.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static List<MunicipioDTO> municipiosDto(List<Municipio> municipios) {
		return municipios.stream().map(MunicipioDTO::new).collect(Collectors.toList());
	}
}

