package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.enums.StateAbbreviation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

	@Query("SELECT DISTINCT city FROM City city WHERE LOWER(city.state.name) LIKE LOWER(CONCAT('%',:stateAbbreviation,'%'))")
	Page<City> findCitiesByStateAbbreviation(String stateAbbreviation, Pageable pagination);

	@Query("SELECT DISTINCT city FROM City city WHERE LOWER(city.state.name) LIKE LOWER(CONCAT('%',:stateAbbreviation,'%'))")
	List<City> findAllCitiesByStateAbbreviation(String stateAbbreviation);
}
