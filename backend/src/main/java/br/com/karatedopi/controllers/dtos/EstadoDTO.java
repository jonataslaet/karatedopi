package br.com.karatedopi.controllers.dtos;

import java.util.List;
import java.util.stream.Collectors;

import br.com.karatedopi.domain.Estado;
import br.com.karatedopi.domain.enums.EstadoEnum;

public class EstadoDTO {
	private Long id;
	private String nome;
	private EstadoEnum uf;

	public EstadoDTO(Estado estado) {
		this.id = estado.getId();
		this.nome = estado.getNome();
		this.uf = estado.getUf();
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

	public EstadoEnum getUf() {
		return uf;
	}

	public void setUf(EstadoEnum uf) {
		this.uf = uf;
	}

	public static List<EstadoDTO> estadosDto(List<Estado> estados) {
		return estados.stream().map(EstadoDTO::new).collect(Collectors.toList());
	}
}
