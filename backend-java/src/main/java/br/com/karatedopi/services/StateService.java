package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.StateDTO;
import br.com.karatedopi.entities.State;
import br.com.karatedopi.repositories.StateRepository;

import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StateService {

    private final StateRepository stateRepository;

    @Cacheable(value = "pagedStates")
    public Page<StateDTO> getPagedStates(Pageable pagination){
        Page<State> foundStates = stateRepository.findAll(pagination);
        return foundStates.map(StateDTO::getStateDTO);
    }

    @Cacheable(value = "allStates")
    public List<StateDTO> getAllStates() {
        return stateRepository.findAll().stream().map(StateDTO::getStateDTO).toList();
    }

    public State findStateByNameOrAbbreviation(String name) {
        return stateRepository.findStateByNameOrAbbreviation(name).orElseThrow(() -> new ResourceNotFoundException("Nenhum estado encontrado com o nome " + name));
    }
}
