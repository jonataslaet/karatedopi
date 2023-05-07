package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.CityDTO;
import br.com.karatedopi.entities.enums.StateAbbreviation;
import br.com.karatedopi.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/cities")
@RequiredArgsConstructor
public class CityController {

	private final CityService cityService;
	
	@GetMapping
	public ResponseEntity<Page<CityDTO>> getPagedCities(@RequestParam(required=false) StateAbbreviation stateAbbreviation, @PageableDefault(sort="id", direction = Direction.DESC) Pageable paginacao){
		Page<CityDTO> pagedCities = cityService.getPagedCities(stateAbbreviation, paginacao);
		return ResponseEntity.ok().body(pagedCities);
	}

	@GetMapping("/all")
	public ResponseEntity<List<CityDTO>> getAllCitiesByState(@RequestParam(required=false) StateAbbreviation stateAbbreviation){
		List<CityDTO> cities = cityService.getAllCitiesByState(stateAbbreviation);
		return ResponseEntity.ok().body(cities);
	}
}