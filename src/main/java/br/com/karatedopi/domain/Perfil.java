package br.com.karatedopi.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Perfil implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
    private Long id;
 
	private String nome;
	private String nomeCompleto;
	
	@ManyToOne
	private Localidade localidade;
 
    @OneToOne
    @MapsId
    private Usuario usuario;
    
    public Perfil() {
    	
    }
    
    public Perfil(String nome, String nomeCompleto) {
		this.nome = nome;
		this.nomeCompleto = nomeCompleto;
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
}
