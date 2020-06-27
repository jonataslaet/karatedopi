package br.com.karatedopi.controllers.cadastros;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.karatedopi.domain.Localidade;

public class CadastroEntidade {
	
	private String nome;
	private String sigla;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFundacao;
	private List<String> contatos = new ArrayList<>();
	private Localidade localidade ;
	
	public CadastroEntidade() {
		
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public LocalDate getDataFundacao() {
		return dataFundacao;
	}

	public void setDataFundacao(LocalDate dataFundacao) {
		this.dataFundacao = dataFundacao;
	}

	public List<String> getContatos() {
		return contatos;
	}

	public void setContatos(List<String> contatos) {
		this.contatos = contatos;
	}

	public Localidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}
	
}
