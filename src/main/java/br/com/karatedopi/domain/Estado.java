package br.com.karatedopi.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.karatedopi.domain.enums.EstadoEnum;

@Entity
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String codigoIBGE;
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	private EstadoEnum uf;
	
	@OneToMany(mappedBy = "estado", cascade = CascadeType.ALL)
	private Set<Municipio> municipios;
	
	public Estado() {
		municipios = new HashSet<>();
	}
	
	public Estado(String codigoIBGE, String descricao, EstadoEnum uf) {
		super();
		this.codigoIBGE = codigoIBGE;
		this.descricao = descricao;
		this.uf = uf;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoIBGE() {
		return codigoIBGE;
	}

	public void setCodigoIBGE(String codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public EstadoEnum getUf() {
		return uf;
	}

	public void setUf(EstadoEnum uf) {
		this.uf = uf;
	}

	public Set<Municipio> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(Set<Municipio> municipios) {
		this.municipios = municipios;
	}
}
