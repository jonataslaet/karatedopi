package br.com.karatedopi.controllers.dtos;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.karatedopi.domain.Papel;
import br.com.karatedopi.domain.Usuario;

public class UsuarioDTO{
	private Long id;
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String senha;
	
	private Set<Papel> papeis = new HashSet<>();
	
	public UsuarioDTO() {
		
	}
	
	public UsuarioDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
		this.papeis = usuario.getPapeis();
	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@JsonIgnore
	public Set<Papel> getPapeis() {
		return papeis;
	}

	public void setPapeis(Set<Papel> papeis) {
		this.papeis = papeis;
	}
}
