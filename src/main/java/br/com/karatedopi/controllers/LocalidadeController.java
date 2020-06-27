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


import br.com.karatedopi.controllers.cadastros.CadastroLocalidade;
import br.com.karatedopi.controllers.dtos.LocalidadeDto;
import br.com.karatedopi.services.LocalidadeService;

@RestController
@RequestMapping(value="/localidades")
public class LocalidadeController {
	@Autowired
	LocalidadeService ls;
	
	// Retorna todos os locais
	@GetMapping
	public ResponseEntity<List<LocalidadeDto>> listarLocais(){
		return ls.retornaLocais();
	}
	
	@PostMapping
	public ResponseEntity<LocalidadeDto> criarLocalidade(@Valid @RequestBody CadastroLocalidade local){
		return ls.criaLocalidade(local);
	}
}
