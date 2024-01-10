package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.StateDTO;
import br.com.karatedopi.entities.State;
import br.com.karatedopi.repositories.StateRepository;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StateService {

	private final StateRepository stateRepository;

	public Page<StateDTO> getStates(Pageable pagination){
		Page<State> foundStates = stateRepository.findAll(pagination);
		return foundStates.map(StateDTO::getStateDTO);
	}

    public List<StateDTO> getAllStates() {
		return stateRepository.findAll().stream().map(StateDTO::getStateDTO).collect(Collectors.toList());
    }

	public State findStateByName(String name) {
		return stateRepository.findStateByName(name).orElseThrow(() -> new ResourceNotFoundException("No state was found"));
	}
}

