package br.com.karatedopi.controllers.dtos;

import java.util.List;
import java.util.stream.Collectors;

import br.com.karatedopi.domain.Estado;
import br.com.karatedopi.domain.enums.EstadoEnum;

public class EstadoDto {
	private Long id; 
	private String codigoIBGE;
	private String descricao;
	private EstadoEnum uf;
	
	public EstadoDto(Estado estado) {
		this.id = estado.getId();
		this.codigoIBGE = estado.getCodigoIBGE();
		this.descricao = estado.getDescricao();
		this.uf = estado.getUf();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoIBGE() {
		return codigoIBGE;
	}

	public void setCodigoIBGE(String codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public EstadoEnum getUf() {
		return uf;
	}

	public void setUf(EstadoEnum uf) {
		this.uf = uf;
	}
	
	public static List<EstadoDto> estadosDto(List<Estado> estados){
		return estados.stream().map(EstadoDto::new).collect(Collectors.toList());
	}
}
