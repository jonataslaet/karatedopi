package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.AssociationInputDTO;
import br.com.karatedopi.controllers.dtos.AssociationOutputDTO;
import br.com.karatedopi.controllers.dtos.AuthenticationResponse;
import br.com.karatedopi.controllers.dtos.RegistrationFormInputDTO;
import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.Association;
import br.com.karatedopi.entities.Federation;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.enums.OrganizationStatus;
import br.com.karatedopi.repositories.AssociationRepository;
import br.com.karatedopi.services.exceptions.AlreadyInUseException;
import br.com.karatedopi.services.exceptions.ForbiddenOperationException;
import br.com.karatedopi.services.exceptions.NoSuchFieldException;
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
public class AssociationService {

    private final FederationService federationService;
    private final AssociationRepository associationRepository;
    private final AddressService addressService;
    private final ProfileService profileService;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public Page<AssociationOutputDTO> getPagedAssociations(Long federationId, Pageable pagination){
        federationService.validExistenceById(federationId);
        Page<Association> associations = associationRepository.findAllByFederationId(federationId, pagination);
        return associations.map(AssociationOutputDTO::getAssociationOutputDTO);
    }

    @Transactional
    public AssociationOutputDTO createAssociation(AssociationInputDTO associationInputDTO) {
        Federation foundFederation = federationService.findFederationByAbbreviation(associationInputDTO.getFederationAbbreviation());
        validNonExistenceByAbbreviation(associationInputDTO.getAssociationAbbreviation());
        Address addressFromInputDTO = addressService.getAddressFromAssociationInputDTO(associationInputDTO);
        addressFromInputDTO = addressService.saveAddress(addressFromInputDTO);
        Association savedAssociation = getAssociationFromInputDTO(associationInputDTO);
        savedAssociation.setStatus(OrganizationStatus.ACTIVE);
        savedAssociation.setAddress(addressFromInputDTO);
        savedAssociation.setFederation(foundFederation);
        savedAssociation = associationRepository.save(savedAssociation);
        return AssociationOutputDTO.getAssociationOutputDTO(savedAssociation);
    }
    
    public AssociationOutputDTO readById(Long associationId) {
        Association foundAssociation = findAssociationById(associationId);
        return AssociationOutputDTO.getAssociationOutputDTO(foundAssociation);
    }

    @Transactional(readOnly = true)
    private Association findAssociationById(Long associationId) {
        return associationRepository.findById(associationId).orElseThrow(() ->
                new ResourceNotFoundException("Nenhuma associação encontrada com o id = " + associationId));
    }

    @Transactional
    public void deleteById(Long associationId) {
        validExistenceById(associationId);
        if (!associationRepository.existsById(associationId)) {
            throw new ResourceNotFoundException("Nenhuma associação encontrada com o id = " + associationId);
        }
        try {
            associationRepository.deleteById(associationId);
        } catch (Exception exception) {
            throw new ResourceStorageException("Problema desconhecido ao deletar associação");
        }
    }

    @Transactional
    public AssociationOutputDTO updateAssociation(Long associationId, AssociationInputDTO associationInputDTO) {
        Association foundAssociation = findAssociationById(associationId);
        Federation foundFederation = federationService.findFederationByAbbreviation(associationInputDTO.getFederationAbbreviation());
        foundAssociation.setFederation(foundFederation);
        validPermissionToUpdateForLoggedUser(foundAssociation.getPresident());
        Address foundAddress = foundAssociation.getAddress();
        Address addressFromAssociationInputDTO = addressService.getAddressFromAssociationInputDTO(associationInputDTO);

        if (!foundAssociation.equals(getAssociationFromInputDTO(associationInputDTO))) {
            updateAssociationFromInputDTO(foundAssociation, associationInputDTO);
            foundAssociation = saveAssociation(foundAssociation);
        }

        if (addressService.areDifferentAddresses(foundAddress, addressFromAssociationInputDTO)) {
            foundAddress = addressService.updateOldAddressFromNewAddress(foundAddress, addressFromAssociationInputDTO);
            foundAssociation.setAddress(foundAddress);
        }

        return AssociationOutputDTO.getAssociationOutputDTO(foundAssociation);
    }

    private void updateAssociationFromInputDTO(Association foundAssociation, AssociationInputDTO associationInputDTO) {
        foundAssociation.setAssociationAbbreviation(associationInputDTO.getAssociationAbbreviation());
        foundAssociation.setBusinessName(associationInputDTO.getBusinessName());
        foundAssociation.setTradeName(associationInputDTO.getTradeName());
        foundAssociation.setFoundationDate(associationInputDTO.getFoundationDate());
        foundAssociation.setEin(associationInputDTO.getEin());
        foundAssociation.setEmail(associationInputDTO.getEmail());
        foundAssociation.setPhoneNumbers(associationInputDTO.getPhoneNumbers()
             .stream().filter(phone -> Objects.nonNull(phone) && !phone.trim().isEmpty()).collect(Collectors.toSet()));

    }

    private Association getAssociationFromInputDTO(AssociationInputDTO associationInputDTO) {
        return Association.builder()
            .businessName(associationInputDTO.getBusinessName())
            .associationAbbreviation(associationInputDTO.getAssociationAbbreviation())
            .tradeName(associationInputDTO.getTradeName())
            .foundationDate(associationInputDTO.getFoundationDate())
            .ein(associationInputDTO.getEin())
            .email(associationInputDTO.getEmail())
            .phoneNumbers(associationInputDTO.getPhoneNumbers()
                .stream().filter(phone -> Objects.nonNull(phone) && !phone.trim().isEmpty()).collect(Collectors.toSet()))
            .build();
    }

    @Transactional
    private Association saveAssociation(Association association) {
        try {
            return associationRepository.save(association);
        } catch (Exception e) {
            throw new ResourceStorageException("Problema desconhecido ao salvar associação");
        }
    }

    private void validPermissionToUpdateForLoggedUser(Profile foundPresident) {
        AuthenticationResponse authenticationResponse = authService.current();
        if (!authenticationResponse.getAuthorities().contains("ROLE_ROOT") &&
                Objects.nonNull(foundPresident) && !foundPresident.getId().equals(authenticationResponse.getId())) {
            throw new ForbiddenOperationException("Apenas o usuário ROOT ou o presidente desta associação podem alterá-la");
        }
    }

    public Association getAssociationFromRegisterDTO(RegistrationFormInputDTO registrationFormDTO) {
        String associationAbbreviation = registrationFormDTO.getProfileInputDTO().getAssociationAbbreviation();
        if (Objects.nonNull(associationAbbreviation) && !associationAbbreviation.trim().isEmpty()) {
            Association association = associationRepository.findAssociationByAbbreviation(associationAbbreviation);
            if (Objects.isNull(association)) {
                throw new ResourceNotFoundException("Não existe associação com a sigla " + associationAbbreviation);
            }
            return association;
        }
        return null;
    }

    public List<String> getAssociationAbbreviations() {
        return associationRepository.findAll().stream()
                .map(Association::getAssociationAbbreviation).collect(Collectors.toList());
    }

    public void validExistenceById(Long federationId) {
        if (!associationRepository.existsById(federationId)) {
            throw new ResourceNotFoundException("Nenhuma associação encontrada com o id = " + federationId);
        }
    }

    public void validNonExistenceByAbbreviation(String associationAbbreviation) {
        if (associationRepository.countByAbbreviation(associationAbbreviation) > 0) {
            throw new AlreadyInUseException("Esta sigla de associação já está sendo usando. Por favor, escolha outra sigla");
        }
    }

    private boolean isBlank(String word) {
        return Objects.isNull(word) || word.trim().isEmpty();
    }

}
