package br.com.karatedopi.controllers.dtos;

import java.util.List;
import java.util.stream.Collectors;

import br.com.karatedopi.domain.Municipio;

public class MunicipioDto {
	private Long id;
	private String descricao;
	private String codigoIBGE;
	private String codigoSIAFI;

	public MunicipioDto(Municipio municipio) {
		this.id = municipio.getId();
		this.descricao = municipio.getDescricao();
		this.codigoIBGE = municipio.getCodigoIBGE();
		this.codigoSIAFI = municipio.getCodigoSIAFI();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigoIBGE() {
		return codigoIBGE;
	}

	public void setCodigoIBGE(String codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}

	public String getCodigoSIAFI() {
		return codigoSIAFI;
	}

	public void setCodigoSIAFI(String codigoSIAFI) {
		this.codigoSIAFI = codigoSIAFI;
	}

	public static List<MunicipioDto> municipiosDto(List<Municipio> municipios) {
		return municipios.stream().map(MunicipioDto::new).collect(Collectors.toList());
	}
}
