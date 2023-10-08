package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.AddressDTO;
import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.State;
import br.com.karatedopi.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Address save(Address address) {
		return addressRepository.save(address);
    }
}

