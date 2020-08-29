package br.com.karatedopi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String nome;
	private String nomeCompleto;
	private String nomeDoPai;
	private String nomeDaMae;
	private String naturalidade; // Piauiense?
	private LocalDate dataNascimento;
	private LocalDate dataCadastro;
	private String cpf;
	private String rg;

	@OneToMany
	private List<TransferenciaDeFaixa> transferenciasDeFaixa = new ArrayList<>();

	@ElementCollection
	private Set<String> contatos = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	private Localidade localidade;

	@OneToOne
	@MapsId
	private Usuario usuario;

	public Perfil() {

	}

	public Perfil(String nome, String nomeCompleto, String nomeDoPai, String nomeDaMae, String naturalidade,
			LocalDate dataNascimento, LocalDate dataCadastro, String cpf, String rg, Set<String> contatos) {
		this.nome = nome;
		this.nomeCompleto = nomeCompleto;
		this.nomeDoPai = nomeDoPai;
		this.nomeDaMae = nomeDaMae;
		this.naturalidade = naturalidade;
		this.dataNascimento = dataNascimento;
		this.setDataCadastro(dataCadastro);
		this.cpf = cpf;
		this.rg = rg;
		this.contatos = contatos;
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

	public List<TransferenciaDeFaixa> getTransferenciasDeFaixa() {
		return transferenciasDeFaixa;
	}

	public void setTransferenciasDeFaixa(List<TransferenciaDeFaixa> transferenciasDeFaixa) {
		this.transferenciasDeFaixa = transferenciasDeFaixa;
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

	public Localidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}
