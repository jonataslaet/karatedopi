package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.StateDTO;
import br.com.karatedopi.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/states")
@RequiredArgsConstructor
public class StateController {

	private final StateService stateService;

	@GetMapping
	public ResponseEntity<Page<StateDTO>> getStates(@PageableDefault(sort="name", direction = Sort.Direction.DESC) Pageable pagination){
		Page<StateDTO> states = stateService.getStates(pagination);
		return ResponseEntity.ok().body(states);
	}

	@GetMapping("/all")
	public ResponseEntity<List<StateDTO>> getAllStates(){
		List<StateDTO> cities = stateService.getAllCities();
		return ResponseEntity.ok().body(cities);
	}
}

