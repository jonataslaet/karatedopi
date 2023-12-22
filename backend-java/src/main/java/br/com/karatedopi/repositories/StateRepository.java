package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.karatedopi.entities.State;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    @Query("SELECT DISTINCT state FROM State state WHERE LOWER(state.name) LIKE LOWER(CONCAT('%',:stateName,'%')) " +
            "OR LOWER(state.stateAbbreviation) LIKE LOWER(CONCAT('%',:stateName,'%'))")
    Optional<State> findStateByName(String stateName);

}
