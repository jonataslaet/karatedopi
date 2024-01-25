package br.com.karatedopi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.karatedopi.entities.State;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    @Query("""
            SELECT DISTINCT state FROM State state 
            WHERE LOWER(state.name) LIKE LOWER(:stateNameOrAbbreviation) 
            OR LOWER(state.stateAbbreviation) LIKE LOWER(:stateNameOrAbbreviation)
            """)
    Optional<State> findStateByNameOrAbbreviation(String stateNameOrAbbreviation);

}
