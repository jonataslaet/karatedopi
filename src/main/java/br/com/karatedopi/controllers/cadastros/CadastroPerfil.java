package br.com.karatedopi.controllers.cadastros;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CadastroPerfil {

	@NotBlank
	@Size(min = 7, max = 64)
	private String nome;

	@NotBlank
	@Size(min = 7, max = 64)
	private String nomeCompleto;
	
	@NotBlank
	@Size(min = 7, max = 64)
	private String nomeDoPai;
	
	@NotNull
	@Size(min = 7, max = 64)
	private String nomeDaMae;
	
	@NotBlank
	@Size(min = 3, max = 64)
	private String naturalidade; //Piauiense?
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCadastro = LocalDate.now();
	
	@CPF
	private String cpf;
	
	@NotBlank
	@Size(min = 3, max = 10)
	private String rg;

	private CadastroLocalidade localidade;
	
	private Set<String> contatos = new HashSet<>();
	
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

	public String getNomeDoPai() {
		return nomeDoPai;
	}

	public void setNomeDoPai(String nomeDoPai) {
		this.nomeDoPai = nomeDoPai;
	}

	public String getNomeDaMae() {
		return nomeDaMae;
	}

	public void setNomeDaMae(String nomeDaMae) {
		this.nomeDaMae = nomeDaMae;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public CadastroLocalidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(CadastroLocalidade localidade) {
		this.localidade = localidade;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Set<String> getContatos() {
		return contatos;
	}

	public void setContatos(Set<String> contatos) {
		this.contatos = contatos;
	}
	
}
