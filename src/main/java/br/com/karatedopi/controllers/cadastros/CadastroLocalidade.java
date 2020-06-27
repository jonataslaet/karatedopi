package br.com.karatedopi.controllers.cadastros;

import br.com.karatedopi.domain.enums.EstadoEnum;

public class CadastroLocalidade {
	private String rua;
	private String numero;
	private String cep;
	private String bairro;
	private String cidade;
	private EstadoEnum uf;
	private String country;
	
	public CadastroLocalidade() {
		
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

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public EstadoEnum getUf() {
		return uf;
	}

	public void setUf(EstadoEnum uf) {
		this.uf = uf;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
