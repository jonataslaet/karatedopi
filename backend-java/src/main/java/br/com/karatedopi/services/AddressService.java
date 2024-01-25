package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.AddressDTO;
import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.State;
import br.com.karatedopi.repositories.AddressRepository;
import br.com.karatedopi.services.exceptions.ResourceStorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

	private final AddressRepository addressRepository;

	private final StateService stateService;

	private final CityService cityService;

	@Transactional(readOnly = true)
	public Page<AddressDTO> getPagedAddresses(String cityName, String stateName, Pageable pagination) {

		if (Objects.nonNull(cityName) && Objects.nonNull(stateName)) {
			State state = stateService.findStateByNameOrAbbreviation(stateName);
			City city = state.getCityByName(cityName);
			Page<Address> addresses = addressRepository.findAddressesByCityName(city.getName(), pagination);
			return addresses.map(AddressDTO::getAddressDTO);
		}

		if (Objects.nonNull(stateName)) {
			List<Long> cityIds = cityService.getAllCitiesByStateNameOrAbbreviation(stateName)
				.stream().map(City::getId).collect(Collectors.toList());
			Page<Address> addresses = addressRepository.findAddressesByCities(cityIds, pagination);
			return addresses.map(AddressDTO::getAddressDTO);
		}

		if (Objects.nonNull(cityName)) {
			List<Long> cityIds = cityService.getAllCitiesByCityName(cityName)
				.stream().map(City::getId).collect(Collectors.toList());
			Page<Address> addresses = addressRepository.findAddressesByCities(cityIds, pagination);
			return addresses.map(AddressDTO::getAddressDTO);
		}

		Page<Address> addresses = addressRepository.findAll(pagination);
		return addresses.map(AddressDTO::getAddressDTO);
	}

	@Transactional
	public Address saveAddress(Address address) {
		try {
			return addressRepository.save(address);
		} catch (Exception e) {
			throw new ResourceStorageException("Problema desconhecido ao salvar endereço");
		}
	}

}

