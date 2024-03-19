package br.com.karatedopi.factories;

import br.com.karatedopi.controllers.dtos.StateDTO;
import br.com.karatedopi.entities.State;
import br.com.karatedopi.entities.enums.StateAbbreviation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FactoryState {

    public static State createState(StateAbbreviation stateAbbreviation) {
        return State.builder().id((long) stateAbbreviation.getId()).stateAbbreviation(stateAbbreviation).name(stateAbbreviation.getName()).build();
    }

    public static List<State> createAllStates() {
        List<State> states = new ArrayList<>();
        for (StateAbbreviation stateAbbreviation: StateAbbreviation.values()) {
            states.add(createState(stateAbbreviation));
        }
        return states;
    }

    public static List<StateDTO> createAllStatesDTOs() {
        return createAllStates().stream().map(StateDTO::getStateDTO).collect(Collectors.toList());
    }

}