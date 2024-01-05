package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.StateDTO;
import br.com.karatedopi.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
	public ResponseEntity<Page<StateDTO>> getStates(@PageableDefault(sort="name", direction = Sort.Direction.DESC) Pageable pagination){
		Page<StateDTO> states = stateService.getStates(pagination);
		return ResponseEntity.ok().body(states);
	}

	@GetMapping("/all")
	public ResponseEntity<List<StateDTO>> getAllStates(){
		List<StateDTO> allStates = stateService.getAllStates();
		return ResponseEntity.ok().body(allStates);
	}
}

