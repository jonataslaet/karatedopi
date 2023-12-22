package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

	@Query("SELECT DISTINCT city FROM City city WHERE LOWER(city.state.name) LIKE LOWER(CONCAT('%',:stateAbbreviation,'%'))")
	Page<City> findCitiesByStateAbbreviation(String stateAbbreviation, Pageable pagination);

	@Query("SELECT DISTINCT city FROM City city WHERE LOWER(city.state.name) LIKE LOWER(CONCAT('%',:stateName,'%')) " +
			"OR LOWER(city.state.stateAbbreviation) LIKE LOWER(CONCAT('%',:stateName,'%'))")
	List<City> findAllCitiesByStateNameOrAbbreviation(@Param("stateName") String stateName);

	@Query("SELECT DISTINCT city FROM City city WHERE LOWER(city.state.name) LIKE LOWER(CONCAT('%',:stateName,'%')) AND " +
			"LOWER(city.name) LIKE LOWER(CONCAT('%',:cityName,'%'))")
	Optional<City> findCityByCityNameAndStateName(@Param("cityName") String cityName, @Param("stateName") String stateName);

	@Query("SELECT DISTINCT city FROM City city WHERE LOWER(city.name) LIKE LOWER(CONCAT('%',:cityName,'%')) ")
	List<City> findAllCitiesByName(@Param("cityName") String cityName);
}
