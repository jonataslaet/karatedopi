package br.com.karatedopi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.karatedopi.controllers.dtos.PerfilDTO;
import br.com.karatedopi.domain.enums.EstadoEnum;
import br.com.karatedopi.services.PerfilService;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

	@Autowired
	private PerfilService perfilService;
	
	@PostMapping
	public ResponseEntity<?> cadastrarPerfil(@RequestBody PerfilDTO perfil){
		return perfilService.cadastrarPerfil(perfil);
	}
	
	@GetMapping
	public ResponseEntity<List<PerfilDTO>> retornarPerfis(){
		return perfilService.retornarPerfis();
	}
	
	@GetMapping
	public Page<PerfilDTO> retornarPerfisPaginados(@RequestParam(required=false) String naturalidade, @PageableDefault(page = 0, size=10, sort="id", direction=Direction.DESC) Pageable paginacao){
		return perfilService.retornarPerfisPaginados(naturalidade, paginacao);
	}
}
