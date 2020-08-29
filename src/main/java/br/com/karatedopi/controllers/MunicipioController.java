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

import br.com.karatedopi.controllers.cadastros.CadastroMunicipio;
import br.com.karatedopi.controllers.dtos.LocalidadeDto;
import br.com.karatedopi.controllers.dtos.MunicipioDto;
import br.com.karatedopi.services.MunicipioService;

@RestController
@RequestMapping(value="/municipios")
public class MunicipioController {
	
	@Autowired
	MunicipioService ms;
//	@GetMapping(value="/{id}/municipios")
//	public ResponseEntity<List<MunicipioDto>> listaMunicipiosDoEstado(@PathVariable Long id){
//		return es.listaMunicipiosDoEstado(id);
//	}
	@GetMapping(value="/{id}/localidades")
	public ResponseEntity<List<LocalidadeDto>> listaLocalidadesDoMunicipio(@PathVariable Long id){
		return ms.listaLocalidadesDoMunicipio(id);
	}

	// Retorna todos os municípios
	@GetMapping
	public ResponseEntity<List<MunicipioDto>> listaMunicipios(){
		return ms.retornaMunicipios();
	}
	
	//Cadastra um novo município
	public ResponseEntity<MunicipioDto> criaMunicipio(@Valid @RequestBody CadastroMunicipio municipio){
		return ms.criaMunicipio(municipio);
	}
}
