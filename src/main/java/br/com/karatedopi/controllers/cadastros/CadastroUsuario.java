package br.com.karatedopi.controllers.cadastros;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CadastroUsuario {

	@NotNull
	@Email
	@Size(min = 7, max = 64)
	private String email;

	@NotBlank
	@Size(min = 7, max = 64)
	private String senha;

	private LocalDateTime dataDeCadastro = LocalDateTime.now();

	public CadastroUsuario() {

	}

	public CadastroUsuario(String email, String senha) {
		this.email = email;
		this.senha = senha;
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

	public LocalDateTime getDataDeCadastro() {
		return dataDeCadastro;
	}

	public void setDataDeCadastro(LocalDateTime dataDeCadastro) {
		this.dataDeCadastro = dataDeCadastro;
	}

}
