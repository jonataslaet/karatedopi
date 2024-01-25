package br.com.karatedopi.factories;

import br.com.karatedopi.entities.State;
import br.com.karatedopi.entities.enums.StateAbbreviation;

public class FactoryState {

    public static State statePiaui() {
        return State.builder().id(17L).stateAbbreviation(StateAbbreviation.PI).name("Piauí").build();
    }

}
