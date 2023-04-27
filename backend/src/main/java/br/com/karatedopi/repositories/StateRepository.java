package br.com.karatedopi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.karatedopi.entities.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long>{

}
