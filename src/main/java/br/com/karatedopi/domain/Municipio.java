package br.com.karatedopi.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Municipio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private String descricao;
	private String codigoIBGE;
	private String codigoSIAFI;
	
	@OneToMany(mappedBy="municipio", cascade = CascadeType.PERSIST)
	private Set<Localidade> localidades;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Estado estado;
	
	public Municipio() {
		localidades = new HashSet<>();
	}
	
	public Municipio(String descricao, String codigoIBGE, String codigoSIAFI) {
		this.descricao = descricao;
		this.codigoIBGE = codigoIBGE;
		this.codigoSIAFI = codigoSIAFI;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigoIBGE() {
		return codigoIBGE;
	}

	public void setCodigoIBGE(String codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}

	public String getCodigoSIAFI() {
		return codigoSIAFI;
	}

	public void setCodigoSIAFI(String codigoSIAFI) {
		this.codigoSIAFI = codigoSIAFI;
	}

	public Set<Localidade> getLocalidades() {
		return localidades;
	}

	public void setLocalidades(Set<Localidade> localidades) {
		this.localidades = localidades;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
