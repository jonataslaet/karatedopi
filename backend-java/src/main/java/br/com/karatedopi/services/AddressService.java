package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.AddressDTO;
import br.com.karatedopi.controllers.dtos.RegistrationFormInputDTO;
import br.com.karatedopi.controllers.dtos.TournamentDTO;
import br.com.karatedopi.controllers.dtos.FederationInputDTO;
import br.com.karatedopi.controllers.dtos.AssociationInputDTO;
import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.State;
import br.com.karatedopi.repositories.AddressRepository;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
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
    public Address updateOldAddressFromNewAddress(Long addressId, Address address) {
        try {
            address.setId(addressId);
            return addressRepository.save(address);
        } catch (Exception e) {
            throw new ResourceStorageException("Problema desconhecido ao atualizar endereço");
        }
    }

    @Transactional
    public Address updateOldAddressFromNewAddress(Address addressOld, Address addressNew) {
        try {
            updateOldAddress(addressOld, addressNew);
            return addressRepository.save(addressOld);
        } catch (Exception e) {
            throw new ResourceStorageException("Problema desconhecido ao atualizar endereço");
        }
    }

    private void updateOldAddress(Address addressOld, Address addressNew) {
        addressOld.setStreet(addressNew.getStreet());
        addressOld.setNumber(addressNew.getNumber());
        addressOld.setZipCode(addressNew.getZipCode());
        addressOld.setNeighbourhood(addressNew.getNeighbourhood());
        addressOld.setCity(addressNew.getCity());
    }

    @Transactional
    public Address saveAddress(Address address) {
        try {
            return addressRepository.save(address);
        } catch (Exception e) {
            throw new ResourceStorageException("Problema desconhecido ao salvar endereço");
        }
    }

    public Address getAddressFromRegisterDTO(RegistrationFormInputDTO registrationFormDTO) {
        State state = stateService.findStateByNameOrAbbreviation(registrationFormDTO.getAddressDTO().getCity().getState().getName());
        City city = getCityInState(registrationFormDTO.getAddressDTO().getCity().getName(), state);
        return Address.builder()
                .street(registrationFormDTO.getAddressDTO().getStreet())
                .number(registrationFormDTO.getAddressDTO().getNumber())
                .zipCode(registrationFormDTO.getAddressDTO().getZipCode())
                .neighbourhood(registrationFormDTO.getAddressDTO().getNeighbourhood())
                .city(city)
                .build();
    }

    public Address getAddressFromTournamentDTO(TournamentDTO tournamentDTO) {
        State state = stateService.findStateByNameOrAbbreviation(tournamentDTO.getAddressDTO().getCity().getState().getName());
        City city = getCityInState(tournamentDTO.getAddressDTO().getCity().getName(), state);
        return Address.builder()
                .street(tournamentDTO.getAddressDTO().getStreet())
                .number(tournamentDTO.getAddressDTO().getNumber())
                .zipCode(tournamentDTO.getAddressDTO().getZipCode())
                .neighbourhood(tournamentDTO.getAddressDTO().getNeighbourhood())
                .city(city)
                .build();
    }

    public City getCityInState(String cityName, State state) {
        return state.getCities().stream().filter(city ->
                cityName.equalsIgnoreCase(city.getName())).findFirst().orElseThrow(() ->
                new ResourceNotFoundException("Nenhuma cidade foi encontrada para este estado"));
    }

    public Address getAddressFromFederationInputDTO(FederationInputDTO federationInputDTO) {
        State state = stateService.findStateByNameOrAbbreviation(federationInputDTO.getAddressDTO().getCity().getState().getName());
        City city = getCityInState(federationInputDTO.getAddressDTO().getCity().getName(), state);
        return Address.builder()
                .street(federationInputDTO.getAddressDTO().getStreet())
                .number(federationInputDTO.getAddressDTO().getNumber())
                .zipCode(federationInputDTO.getAddressDTO().getZipCode())
                .neighbourhood(federationInputDTO.getAddressDTO().getNeighbourhood())
                .city(city)
                .build();
    }

    public Boolean areDifferentAddresses(Address address1, Address address2) {
        return !address1.getStreet().equalsIgnoreCase(address2.getStreet()) ||
                !address1.getNumber().equalsIgnoreCase(address2.getNumber()) ||
                !address1.getZipCode().equalsIgnoreCase(address2.getZipCode()) ||
                !address1.getNeighbourhood().equalsIgnoreCase(address2.getNeighbourhood()) ||
                !address1.getCity().getName().equalsIgnoreCase(address2.getCity().getName()) ||
                !address1.getCity().getState().getName().equalsIgnoreCase(address2.getCity().getState().getName());
    }

    public Address getAddressFromAssociationInputDTO(AssociationInputDTO associationInputDTO) {
        State state = stateService.findStateByNameOrAbbreviation(associationInputDTO.getAddressDTO().getCity().getState().getName());
        City city = getCityInState(associationInputDTO.getAddressDTO().getCity().getName(), state);
        return Address.builder()
                .street(associationInputDTO.getAddressDTO().getStreet())
                .number(associationInputDTO.getAddressDTO().getNumber())
                .zipCode(associationInputDTO.getAddressDTO().getZipCode())
                .neighbourhood(associationInputDTO.getAddressDTO().getNeighbourhood())
                .city(city)
                .build();
    }
}
