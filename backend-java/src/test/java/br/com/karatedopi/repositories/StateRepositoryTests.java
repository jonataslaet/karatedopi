package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.State;
import br.com.karatedopi.entities.enums.StateAbbreviation;
import br.com.karatedopi.factories.FactoryState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class StateRepositoryTests {

    @Autowired
    private StateRepository stateRepository;

    private Long existingStatePiauiId;

    private State statePiaui;

    @BeforeEach
    public void setUp() {
        existingStatePiauiId = 17L;
        statePiaui = FactoryState.statePiaui();
    }

    @ParameterizedTest
    @EnumSource(StateAbbreviation.class)
    public void findShouldFindStateByName(StateAbbreviation stateAbbreviation) {
        String abbreviation = stateAbbreviation.toString();
        State foundState = stateRepository.findStateByNameOrAbbreviation(abbreviation).orElse(null);
        Assertions.assertNotNull(foundState);
        Assertions.assertEquals(foundState.getStateAbbreviation(), stateAbbreviation);
    }
}
