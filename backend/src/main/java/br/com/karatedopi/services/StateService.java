package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.StateDTO;
import br.com.karatedopi.entities.State;
import br.com.karatedopi.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StateService {
	@Autowired
	StateRepository stateRepository;

	public Page<StateDTO> getStates(Pageable pagination){
		Page<State> foundStates = stateRepository.findAll(pagination);
		return foundStates.map(StateDTO::getStateDTO);
	}
}

