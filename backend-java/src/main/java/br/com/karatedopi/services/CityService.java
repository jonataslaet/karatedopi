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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    public List<City> getAllCitiesByStateNameOrAbbreviation(String stateName) {
        List<City> allCitiesByState = cityRepository.findAllCitiesByStateNameOrAbbreviation(stateName);
        return allCitiesByState;
    }

    public City getCityByCityNameAndStateName(String city, String state) {
        return cityRepository.findCityByCityNameAndStateName(city, state).orElseThrow(() -> new ResourceNotFoundException("Nenhuma cidade encontrada para este estado"));
    }

    public List<City> getAllCitiesByCityName(String cityName) {
        List<City> allCitiesByName = cityRepository.findAllCitiesByName(cityName);
        if (Objects.isNull(allCitiesByName) || allCitiesByName.isEmpty()) throw new ResourceNotFoundException("Nenhuma cidade encontrada para este estado");
        return allCitiesByName;
    }
}
