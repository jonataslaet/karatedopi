package br.com.karatedopi.controllers.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.karatedopi.domain.Perfil;

public class PerfilDTO {

	private Long id;
	private String nome;
	private String nomeCompleto;
	private String nomeDoPai;
	private String nomeDaMae;
	private String naturalidade;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")  
	private LocalDate dataNascimento;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm")
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime dataCadastro;
	
	private String cpf;
	private String rg;
	private Set<String> contatos = new HashSet<>();

	public PerfilDTO() {
		this.dataCadastro = LocalDateTime.now();
	}
	
	public PerfilDTO(Perfil perfil) {
		this.nome = perfil.getNome();
		this.nomeCompleto = perfil.getNomeCompleto();
		this.nomeDoPai = perfil.getNomeDoPai();
		this.nomeDaMae = perfil.getNomeDaMae();
		this.naturalidade = perfil.getNaturalidade();
		this.dataNascimento = perfil.getDataNascimento();
		this.dataCadastro = perfil.getDataCadastro();
		this.cpf = perfil.getCpf();
		this.rg = perfil.getRg();
	}

//	@JsonIgnore
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

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
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

	public Set<String> getContatos() {
		return contatos;
	}

	public void setContatos(Set<String> contatos) {
		this.contatos = contatos;
	}

}
