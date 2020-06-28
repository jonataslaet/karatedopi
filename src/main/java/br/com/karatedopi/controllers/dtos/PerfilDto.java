package br.com.karatedopi.controllers.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.karatedopi.domain.Perfil;

public class PerfilDto {
	
	private Long id;
	private String nome;
	private String nomeCompleto;
	private String nomeDoPai;
	private String nomeDaMae;
	private String naturalidade; //Piauiense?
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	private String cpf;
	private String rg;
	
	public PerfilDto(Perfil perfil) {
		this.id = perfil.getId();
		this.nome = perfil.getNome();
		this.nomeCompleto = perfil.getNomeCompleto();
		this.nomeDoPai = perfil.getNomeDoPai();
		this.nomeDaMae = perfil.getNomeDaMae();
		this.naturalidade = perfil.getNaturalidade();
		this.dataNascimento = perfil.getDataNascimento();
		this.cpf = perfil.getCpf();
		this.rg = perfil.getRg();
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

	public static List<PerfilDto> perfisDto(List<Perfil> perfis){
		return perfis.stream().map(PerfilDto::new).collect(Collectors.toList());
	}

}
