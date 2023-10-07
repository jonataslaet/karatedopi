package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.AddressDTO;
import br.com.karatedopi.controllers.dtos.CityDTO;
import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.State;
import br.com.karatedopi.entities.enums.StateAbbreviation;
import br.com.karatedopi.repositories.AddressRepository;
import br.com.karatedopi.repositories.CityRepository;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

	private final AddressRepository addressRepository;

	private final StateService stateService;
	
	public Page<AddressDTO> getPagedAddresses(String cityName, String stateName, Pageable pagination) {
		State state = stateService.findStateByName(stateName);
		City city = state.getCityByName(cityName);
		Page<Address> addresses = addressRepository.findAddressesByCityName(city.getName(), pagination);
		return addresses.map(AddressDTO::getAddressDTO);
	}
}

