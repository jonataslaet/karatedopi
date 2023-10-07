package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.StateDTO;
import br.com.karatedopi.entities.State;
import br.com.karatedopi.repositories.StateRepository;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateService {
	@Autowired
	StateRepository stateRepository;

	public Page<StateDTO> getStates(Pageable pagination){
		Page<State> foundStates = stateRepository.findAll(pagination);
		return foundStates.map(StateDTO::getStateDTO);
	}

    public List<StateDTO> getAllCities() {
		return stateRepository.findAll().stream().map(StateDTO::getStateDTO).collect(Collectors.toList());
    }

	public State findStateByName(String name) {
		return stateRepository.findStateByName(name).orElseThrow(() -> new ResourceNotFoundException("No state was found"));
	}
}

