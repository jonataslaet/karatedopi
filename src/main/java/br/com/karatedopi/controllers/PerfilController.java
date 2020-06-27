package br.com.karatedopi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karatedopi.controllers.cadastros.CadastroPerfil;
import br.com.karatedopi.controllers.dtos.PerfilDto;
import br.com.karatedopi.services.PerfilService;

@RestController
@RequestMapping(value="/perfis")
public class PerfilController {
	@Autowired
	PerfilService us;
	
	@GetMapping(value ="/{id}")
	public ResponseEntity<PerfilDto> buscaPerfil(@PathVariable Long id){
		return us.retornaPerfil(id);
	}
	
	// Retorna todos os usuários
	@GetMapping
	public ResponseEntity<List<PerfilDto>> listaPerfis(){
		return us.retornaPerfis();
	}

	// Cadastra um novo usuario
	@PostMapping
	public ResponseEntity<PerfilDto> criaPerfil(@Valid @RequestBody CadastroPerfil usuario) {
		return us.criarPerfil(usuario);
	}
}
