package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.CityDTO;
import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.enums.StateAbbreviation;
import br.com.karatedopi.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

	public List<CityDTO> getAllCitiesByState(StateAbbreviation stateAbbreviation) {
		return cityRepository.findAllCitiesByStateAbbreviation(stateAbbreviation.getName()).stream().map(CityDTO::getCityDTO).collect(Collectors.toList());
	}
}

