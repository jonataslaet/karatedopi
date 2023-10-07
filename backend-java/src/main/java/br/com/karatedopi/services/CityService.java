package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.CityDTO;
import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.enums.StateAbbreviation;
import br.com.karatedopi.repositories.CityRepository;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {

	private final CityRepository cityRepository;
	
	public Page<CityDTO> getPagedCities(StateAbbreviation stateAbbreviation, Pageable pagination){
		if (stateAbbreviation == null) {
			Page<City> cities = cityRepository.findAll(pagination);
			return cities.map(CityDTO::getCityDTO);
		}
		Page<City> cities = cityRepository.findCitiesByStateAbbreviation(stateAbbreviation.getName(), pagination);
		return cities.map(CityDTO::getCityDTO);
	}

	public List<CityDTO> getAllCitiesDTOByState(StateAbbreviation stateAbbreviation) {
		return this.getAllCitiesByState(stateAbbreviation).stream().map(CityDTO::getCityDTO).collect(Collectors.toList());
	}

	public List<City> getAllCitiesByState(StateAbbreviation stateAbbreviation) {
		List<City> allCitiesByState = cityRepository.findAllCitiesByStateAbbreviation(stateAbbreviation.getName());
		if (Objects.isNull(allCitiesByState) || allCitiesByState.isEmpty()) throw new ResourceNotFoundException("No city was found for this state");
		return allCitiesByState;
	}
}

