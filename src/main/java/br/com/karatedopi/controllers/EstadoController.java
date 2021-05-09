package br.com.karatedopi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karatedopi.controllers.dtos.EstadoDTO;
import br.com.karatedopi.controllers.dtos.MunicipioDTO;
import br.com.karatedopi.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoController {

	@Autowired
	private EstadoService estadoService;

	@GetMapping
	public ResponseEntity<List<EstadoDTO>> listaEstados(){
		return estadoService.retornaEstados();
	}
	
	@GetMapping(value="/{id}/municipios")
	public ResponseEntity<List<MunicipioDTO>> listaMunicipiosDoEstado(@PathVariable Long id){
		return estadoService.listaMunicipiosDoEstado(id);
	}
}

