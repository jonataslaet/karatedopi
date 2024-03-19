package br.com.karatedopi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.karatedopi.controllers.dtos.StateDTO;
import br.com.karatedopi.entities.State;
import br.com.karatedopi.entities.enums.StateAbbreviation;
import br.com.karatedopi.factories.FactoryState;
import br.com.karatedopi.repositories.StateRepository;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class StateServiceTests {

    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private StateService stateService;

    @Test
    public void testFindStateByNameOrAbbreviationShouldReturnStateIfExists() {
        String stateName = "Piauí";
        State state = FactoryState.createState(StateAbbreviation.PI);
        when(stateRepository.findStateByNameOrAbbreviation(stateName)).thenReturn(Optional.of(state));
        State foundState = stateService.findStateByNameOrAbbreviation(stateName);
        assertNotNull(foundState);
        assertEquals(state.getId(), foundState.getId());
        assertEquals(state.getStateAbbreviation(), foundState.getStateAbbreviation());
        assertEquals(state.getName(), foundState.getName());
    }

    @Test
    public void testFindStateByNameOrAbbreviationShouldThrowExceptionIfStateNotFound() {
        String stateName = "NonexistentState";
        when(stateRepository.findStateByNameOrAbbreviation(stateName)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            stateService.findStateByNameOrAbbreviation(stateName);
        });
    }

    @Test
    public void testAllStatesShouldReturnListOfStates() {
        State state1 = FactoryState.createState(StateAbbreviation.AC);
        State state2 = FactoryState.createState(StateAbbreviation.AL);
        List<State> states = Arrays.asList(state1, state2);
        when(stateRepository.findAll()).thenReturn(states);

        List<StateDTO> stateDTOs = stateService.getAllStates();

        assertNotNull(stateDTOs);
        assertEquals(2, stateDTOs.size());
        assertEquals(state1.getId(), stateDTOs.get(0).getId());
        assertEquals(state1.getStateAbbreviation(), stateDTOs.get(0).getStateAbbreviation());
        assertEquals(state1.getName(), stateDTOs.get(0).getName());
        assertEquals(state2.getId(), stateDTOs.get(1).getId());
        assertEquals(state2.getStateAbbreviation(), stateDTOs.get(1).getStateAbbreviation());
        assertEquals(state2.getName(), stateDTOs.get(1).getName());
    }

    @Test
    public void testGetStatesShouldReturnPageOfStateDTOs() {
        List<State> states = FactoryState.createAllStates();
        Pageable pageable = mock(Pageable.class);
        Page<State> page = new PageImpl<>(states, pageable, states.size());
        when(stateRepository.findAll(pageable)).thenReturn(page);
        Page<StateDTO> resultPage = stateService.getStates(pageable);
        assertNotNull(resultPage);
        assertEquals(states.size(), resultPage.getContent().size());
        List<StateDTO> expectedDTOs = states.stream()
                .map(StateDTO::getStateDTO)
                .toList();
        List<StateDTO> actualDTOs = resultPage.getContent();
        for (int i = 0; i < expectedDTOs.size(); i++) {
            assertEquals(expectedDTOs.get(i).getId(), actualDTOs.get(i).getId());
            assertEquals(expectedDTOs.get(i).getStateAbbreviation(), actualDTOs.get(i).getStateAbbreviation());
            assertEquals(expectedDTOs.get(i).getName(), actualDTOs.get(i).getName());
        }
    }
}

