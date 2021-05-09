package br.com.karatedopi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;

import br.com.karatedopi.controllers.dtos.PerfilDTO;

@Entity
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String nome;
	private String nomeCompleto;
	private String nomeDoPai;
	private String nomeDaMae;
	private String naturalidade;
	private LocalDate dataNascimento;
	private LocalDateTime dataCadastro;
	private String cpf;
	private String rg;

	@ElementCollection(fetch=FetchType.EAGER)
	private Set<String> contatos = new HashSet<>();

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Usuario usuario;

	public Perfil() {

	}

	public Perfil(String nome, String nomeCompleto, String nomeDoPai, String nomeDaMae, String naturalidade,
			LocalDate dataNascimento, LocalDateTime dataCadastro, String cpf, String rg, Usuario usuario) {
		this.nome = nome;
		this.nomeCompleto = nomeCompleto;
		this.nomeDoPai = nomeDoPai;
		this.nomeDaMae = nomeDaMae;
		this.naturalidade = naturalidade;
		this.dataNascimento = dataNascimento;
		this.dataCadastro = dataCadastro;
		this.cpf = cpf;
		this.rg = rg;
		this.usuario = usuario;
	}

	public Perfil(PerfilDTO perfilDTO) {
		this.nome = perfilDTO.getNome();
		this.nomeCompleto = perfilDTO.getNomeCompleto();
		this.nomeDoPai = perfilDTO.getNomeDoPai();
		this.nomeDaMae = perfilDTO.getNomeDaMae();
		this.naturalidade = perfilDTO.getNaturalidade();
		this.dataNascimento = perfilDTO.getDataNascimento();
		this.dataCadastro = perfilDTO.getDataCadastro();
		this.cpf = perfilDTO.getCpf();
		this.rg = perfilDTO.getRg();
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Perfil other = (Perfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
