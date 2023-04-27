package br.com.karatedopi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.karatedopi.controllers.dtos.CityDTO;
import br.com.karatedopi.entities.enums.StateAbbreviation;
import br.com.karatedopi.services.CityService;

@RestController
@RequestMapping(value="/cities")
@RequiredArgsConstructor
public class CityController {

	private final CityService cityService;
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	public Page<CityDTO> getCities(@RequestParam(required=false) StateAbbreviation stateAbbreviation, @PageableDefault(page = 0, size=10, sort="id", direction = Direction.DESC) Pageable paginacao){
		return cityService.getCities(stateAbbreviation, paginacao);
	}
	
	
}