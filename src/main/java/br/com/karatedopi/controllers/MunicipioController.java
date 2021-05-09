package br.com.karatedopi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karatedopi.controllers.dtos.MunicipioDTO;
import br.com.karatedopi.services.EstadoService;
import br.com.karatedopi.services.MunicipioService;

@RestController
@RequestMapping(value="/municipios")
public class MunicipioController {
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private MunicipioService municipioService;
	
	@GetMapping
	public ResponseEntity<List<MunicipioDTO>> listaMunicipios(){
		return municipioService.retornaMunicipios();
	}
}