package br.com.karatedopi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.karatedopi.controllers.cadastros.CadastroEntidade;
import br.com.karatedopi.controllers.dtos.EntidadeDto;
import br.com.karatedopi.services.EntidadeService;

@RestController
@RequestMapping(value="/entidades")
public class EntidadeController {
	@Autowired
	EntidadeService ls;
	
	// Retorna todos os entidades
	@GetMapping
	public ResponseEntity<List<EntidadeDto>> listarEntidades(){
		return ls.retornaEntidades();
	}
	
	@PostMapping
	public ResponseEntity<EntidadeDto> criarEntidade(@Valid @RequestBody CadastroEntidade local){
		return ls.criaEntidade(local);
	}
}
