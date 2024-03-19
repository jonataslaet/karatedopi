package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.State;
import br.com.karatedopi.entities.enums.StateAbbreviation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class StateRepositoryTests {

    @Autowired
    private StateRepository stateRepository;

    @ParameterizedTest
    @EnumSource(StateAbbreviation.class)
    public void testFindShouldFindStateByName(StateAbbreviation stateAbbreviation) {
        String abbreviation = stateAbbreviation.getName();
        State foundState = stateRepository.findStateByNameOrAbbreviation(abbreviation).orElse(null);
        assertNotNull(foundState);
        assertEquals(foundState.getStateAbbreviation(), stateAbbreviation);
    }

    @ParameterizedTest
    @EnumSource(StateAbbreviation.class)
    public void testFindShouldFindStateByAbbreviation(StateAbbreviation stateAbbreviation) {
        String abbreviation = stateAbbreviation.toString();
        State foundState = stateRepository.findStateByNameOrAbbreviation(abbreviation).orElse(null);
        assertNotNull(foundState);
        assertEquals(foundState.getStateAbbreviation(), stateAbbreviation);
    }
}