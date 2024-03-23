package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.AuthenticationResponse;
import br.com.karatedopi.controllers.dtos.FederationInputDTO;
import br.com.karatedopi.controllers.dtos.FederationOutputDTO;
import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.Federation;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.enums.OrganizationStatus;
import br.com.karatedopi.repositories.FederationRepository;
import br.com.karatedopi.services.exceptions.ForbiddenOperationException;
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
public class FederationService {

    private final FederationRepository federationRepository;
    private final AddressService addressService;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public Page<FederationOutputDTO> getPagedFederations(String search, String status, Pageable pagination){
        return federationRepository.findAllBySearchContent(search, status, pagination).map(FederationOutputDTO::getFederationOutputDTO);
    }

    @Transactional
    public FederationOutputDTO createFederation(FederationInputDTO federationInputDTO) {
        Address addressFromInputDTO = addressService.getAddressFromFederationInputDTO(federationInputDTO);
        addressFromInputDTO = addressService.saveAddress(addressFromInputDTO);
        Federation savedFederation = getFederationFromInputDTO(federationInputDTO);
        savedFederation.setStatus(OrganizationStatus.ACTIVE);
        savedFederation.setAddress(addressFromInputDTO);
        savedFederation = federationRepository.save(savedFederation);
        return FederationOutputDTO.getFederationOutputDTO(savedFederation);
    }
    
    public FederationOutputDTO readById(Long federationId) {
        Federation foundFederation = findFederationById(federationId);
        return FederationOutputDTO.getFederationOutputDTO(foundFederation);
    }

    @Transactional(readOnly = true)
    public Federation findFederationById(Long federationId) {
        return federationRepository.findById(federationId).orElseThrow(() ->
                new ResourceNotFoundException("Nenhuma federação encontrada com o id = " + federationId));
    }

    @Transactional
    public void deleteById(Long federationId) {
        validExistenceById(federationId);
        try {
            federationRepository.deleteById(federationId);
        } catch (Exception exception) {
            throw new ResourceStorageException("Problema desconhecido ao deletar federação");
        }
    }

    @Transactional
    public FederationOutputDTO updateFederation(Long federationId, FederationInputDTO federationInputDTO) {
        Federation foundFederation = findFederationById(federationId);

        validPermissionToUpdateForLoggedUser(foundFederation.getPresident());
        Address foundAddress = foundFederation.getAddress();
        Address addressFromFederationInputDTO = addressService.getAddressFromFederationInputDTO(federationInputDTO);

        if (!foundFederation.equals(getFederationFromInputDTO(federationInputDTO))) {
            updateFederationFromInputDTO(foundFederation, federationInputDTO);
            foundFederation = saveFederation(foundFederation);
        }

        if (addressService.areDifferentAddresses(foundAddress, addressFromFederationInputDTO)) {
            foundAddress = addressService.updateOldAddressFromNewAddress(foundAddress, addressFromFederationInputDTO);
            foundFederation.setAddress(foundAddress);
        }

        return FederationOutputDTO.getFederationOutputDTO(foundFederation);
    }

    private void validPermissionToUpdateForLoggedUser(Profile foundPresident) {
        AuthenticationResponse authenticationResponse = authService.current();
        if (!authenticationResponse.getAuthorities().contains("ROLE_ROOT") &&
                Objects.nonNull(foundPresident) && !foundPresident.getId().equals(authenticationResponse.getId())) {
            throw new ForbiddenOperationException("Apenas o usuário ROOT ou o presidente desta federação podem alterá-la");
        }
    }

    private void updateFederationFromInputDTO(Federation foundFederation, FederationInputDTO federationInputDTO) {
        foundFederation.setFederationAbbreviation(federationInputDTO.getFederationAbbreviation());
        foundFederation.setBusinessName(federationInputDTO.getBusinessName());
        foundFederation.setTradeName(federationInputDTO.getTradeName());
        foundFederation.setFoundationDate(federationInputDTO.getFoundationDate());
        foundFederation.setEin(federationInputDTO.getEin());
        foundFederation.setEmail(federationInputDTO.getEmail());
        foundFederation.setPhoneNumbers(federationInputDTO.getPhoneNumbers()
            .stream().filter(phone -> Objects.nonNull(phone) && !phone.trim().isEmpty()).collect(Collectors.toSet()));

    }

    private Federation getFederationFromInputDTO(FederationInputDTO federationInputDTO) {
        return Federation.builder()
                .businessName(federationInputDTO.getBusinessName())
                .federationAbbreviation(federationInputDTO.getFederationAbbreviation())
                .tradeName(federationInputDTO.getTradeName())
                .foundationDate(federationInputDTO.getFoundationDate())
                .ein(federationInputDTO.getEin())
                .email(federationInputDTO.getEmail())
                .phoneNumbers(federationInputDTO.getPhoneNumbers())
                .build();
    }

    @Transactional
    private Federation saveFederation(Federation federation) {
        try {
            return federationRepository.save(federation);
        } catch (Exception e) {
            throw new ResourceStorageException("Problema desconhecido ao salvar federação");
        }
    }

    public void validExistenceById(Long federationId) {
        if (!federationRepository.existsById(federationId)) {
            throw new ResourceNotFoundException("Nenhuma federação encontrada com o id = " + federationId);
        }
    }

    public List<String> getFederationsAbbreviations() {
        return federationRepository.findAll().stream()
                .map(Federation::getFederationAbbreviation).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Federation findFederationByAbbreviation(String federationAbbreviation) {
        if (Objects.isNull(federationAbbreviation) || federationAbbreviation.trim().isEmpty()) return null;
        return federationRepository.findByAbbreviation(federationAbbreviation).orElseThrow(() ->
                new ResourceNotFoundException("Nenhuma federação encontrada com a sigla " + federationAbbreviation));
    }

}
