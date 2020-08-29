package br.com.karatedopi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karatedopi.controllers.cadastros.CadastroEstado;
import br.com.karatedopi.controllers.dtos.EstadoDto;
import br.com.karatedopi.controllers.dtos.MunicipioDto;
import br.com.karatedopi.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoController {

	@Autowired
	EstadoService es;

	// Retorna todos os estados
	@GetMapping
	public ResponseEntity<List<EstadoDto>> listaEstados(){
		return es.retornaEstados();
	}
	
	@GetMapping(value="/{id}/municipios")
	public ResponseEntity<List<MunicipioDto>> listaMunicipiosDoEstado(@PathVariable Long id){
		return es.listaMunicipiosDoEstado(id);
	}
	
	//Cadastra um novo município
	public ResponseEntity<EstadoDto> criaEstado(@Valid @RequestBody CadastroEstado municipio){
		return es.criaEstado(municipio);
	}
}
