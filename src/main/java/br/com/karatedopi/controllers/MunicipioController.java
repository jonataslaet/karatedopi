package br.com.karatedopi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.karatedopi.controllers.dtos.MunicipioDTO;
import br.com.karatedopi.domain.enums.EstadoEnum;
import br.com.karatedopi.services.MunicipioService;

@RestController
@RequestMapping(value="/municipios")
public class MunicipioController {
	
	@Autowired
	private MunicipioService municipioService;
	
	@GetMapping
	public Page<MunicipioDTO> listaMunicipios(@RequestParam(required=false) EstadoEnum uf, @PageableDefault(page = 0, size=10, sort="id", direction = Direction.DESC) Pageable paginacao){
		return municipioService.retornaMunicipios(uf, paginacao);
	}
	
	
}