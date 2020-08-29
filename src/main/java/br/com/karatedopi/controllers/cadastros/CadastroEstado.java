package br.com.karatedopi.controllers.cadastros;

import br.com.karatedopi.domain.enums.EstadoEnum;

public class CadastroEstado {
	private String codigoIBGE;
	private String descricao;
	private EstadoEnum uf;
	
	public CadastroEstado() {
	}

	public CadastroEstado(String codigoIBGE, String descricao, EstadoEnum uf) {
		super();
		this.codigoIBGE = codigoIBGE;
		this.descricao = descricao;
		this.uf = uf;
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
	
}
