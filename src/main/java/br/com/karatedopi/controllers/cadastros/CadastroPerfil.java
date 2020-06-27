package br.com.karatedopi.controllers.cadastros;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CadastroPerfil {

	@NotBlank
	@Size(min = 7, max = 64)
	private String nome;

	@NotNull
	@Size(min = 7, max = 64)
	private String nomeCompleto;

	public CadastroPerfil() {

	}

	public CadastroPerfil(String nome, String nomeCompleto) {
		this.nome = nome;
		this.nomeCompleto = nomeCompleto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	
}
