package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.StateDTO;
import br.com.karatedopi.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/states")
@RequiredArgsConstructor
public class StateController {

	private final StateService stateService;

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	public Page<StateDTO> getStates(@PageableDefault(page = 0, size=10, sort="name", direction = Sort.Direction.DESC) Pageable pagination){
		return stateService.getStates(pagination);
	}
}

