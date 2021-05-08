package br.com.karatedopi.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Papel implements Serializable, GrantedAuthority{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	public Papel() {
	}
	
	public Papel(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public String getAuthority() {
		return this.nome;
	}
}
