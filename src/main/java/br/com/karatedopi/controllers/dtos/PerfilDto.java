package br.com.karatedopi.controllers.dtos;

import java.util.List;
import java.util.stream.Collectors;

import br.com.karatedopi.domain.Perfil;

public class PerfilDto {
	
	private Long id;
	private String nome;
	private String nomeCompleto;
	
	public PerfilDto(Perfil perfil) {
		this.id = perfil.getId();
		this.nome = perfil.getNome();
		this.nomeCompleto = perfil.getNomeCompleto();
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

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public static List<PerfilDto> perfisDto(List<Perfil> perfis){
		return perfis.stream().map(PerfilDto::new).collect(Collectors.toList());
	}

}
