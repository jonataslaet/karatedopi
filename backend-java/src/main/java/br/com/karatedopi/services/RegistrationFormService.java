package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.AuthenticationResponse;
import br.com.karatedopi.controllers.dtos.RegistrationFormOutputDTO;
import br.com.karatedopi.controllers.dtos.RegistrationFormInputDTO;
import br.com.karatedopi.entities.Association;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.enums.UserStatus;
import br.com.karatedopi.services.exceptions.AlreadyInUseException;
import br.com.karatedopi.services.exceptions.ForbiddenOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationFormService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final AddressService addressService;
    private final AssociationService associationService;
    private final AuthService authService;

    @Transactional
    public RegistrationFormOutputDTO createRegistration(RegistrationFormInputDTO registrationFormDTO) {
        validateNonExistingEmail(registrationFormDTO.getUserInputDTO().getEmail());

        User user = getUserFromRegistrationInputDTO(registrationFormDTO);
        Profile profile = getProfileFromRegistrationInputDTO(registrationFormDTO);
        Address address = addressService.getAddressFromRegisterDTO(registrationFormDTO);
        Association association = associationService.getAssociationFromRegisterDTO(registrationFormDTO);

        associateAssociationAndUserAndProfileAndAddress(association, user, profile, address);

        addressService.saveAddress(address);
        User savedUser = userService.saveUser(user);
        return RegistrationFormOutputDTO.getRegistrationFormOutputDTO(savedUser);
    }

    @Transactional
    public void deleteRegistrationByUserId(Long userId) {
        userService.deleteUserById(userId);
    }

    private void validateNonExistingEmail(String email) {
        if (userService.userExistsByEmail(email)) {
            throw new AlreadyInUseException("Email já existe");
        }
    }

    private User getUserFromRegistrationInputDTO(RegistrationFormInputDTO registrationFormInputDTO) {
        User user = User.builder()
                .email(registrationFormInputDTO.getUserInputDTO().getEmail())
                .firstname(getFirstname(registrationFormInputDTO.getUserInputDTO().getFirstname(), registrationFormInputDTO.getProfileInputDTO().getFullname()))
                .lastname(getLastname(registrationFormInputDTO.getUserInputDTO().getLastname(), registrationFormInputDTO.getProfileInputDTO().getFullname()))
                .status(UserStatus.PENDING_EVALUATION)
                .roles(new HashSet<>())
                .build();
        String passwordFromDTO = registrationFormInputDTO.getUserInputDTO().getPassword();
        if (isNotNullAndNotEmpty(passwordFromDTO)) {
            user.setPassword(passwordEncoder.encode(passwordFromDTO));
        }
        user.getRoles().add(roleService.getRoleUser());
        return user;
    }

    private Profile getProfileFromRegistrationInputDTO(RegistrationFormInputDTO registrationFormDTO) {
        return Profile.builder()
                .fullname(registrationFormDTO.getProfileInputDTO().getFullname())
                .mother(registrationFormDTO.getProfileInputDTO().getMother())
                .father(registrationFormDTO.getProfileInputDTO().getFather())
                .bloodType(registrationFormDTO.getProfileInputDTO().getBloodType())
                .birthday(registrationFormDTO.getProfileInputDTO().getBirthday())
                .itin(registrationFormDTO.getProfileInputDTO().getItin())
                .nid(registrationFormDTO.getProfileInputDTO().getNid())
                .phoneNumbers(registrationFormDTO.getProfileInputDTO().getPhoneNumbers())
                .build();
    }

    private String getFirstname(String firstname, String fullname) {
        if (isNotNullAndNotEmpty(firstname)) return firstname;
        String name = getPiecesOfFullname(fullname)[0];
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    private String getLastname(String lastname, String fullname) {
        if (isNotNullAndNotEmpty(lastname)) return lastname;
        String[] piecesOfTheFullname = getPiecesOfFullname(fullname);
        String name = piecesOfTheFullname[piecesOfTheFullname.length - 1];
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    private String[] getPiecesOfFullname(String fullname) {
        if (isInvalidFullname(fullname)) {
            return new String[0];
        }
        return fullname.split(" ");
    }

    private static boolean isInvalidFullname(String fullname) {
        return fullname == null || fullname.trim().isEmpty();
    }

    public void updateRegistrationForm(RegistrationFormInputDTO registrationFormDTO) {
        AuthenticationResponse loggedUser = authService.current();
        updateRegistrationFormByUserId(loggedUser.getId(), registrationFormDTO);
    }

    @Transactional
    public void updateRegistrationFormByUserId(Long userId, RegistrationFormInputDTO registrationFormInputDTO) {
        User user = userService.getUser(userId);
        Address address = user.getProfile().getAddress();
        Association association = user.getProfile().getAssociation();

        User userFromRegisterFromInputDTO = getUserFromRegistrationInputDTO(registrationFormInputDTO);
        Profile profileFromRegisterFormInputDTO = getProfileFromRegistrationInputDTO(registrationFormInputDTO);
        Address addressFromRegisterDTO = addressService.getAddressFromRegisterDTO(registrationFormInputDTO);
        Association associationFromRegisterDTO = associationService.getAssociationFromRegisterDTO(registrationFormInputDTO);

        associateAssociationAndUserAndProfileAndAddress(associationFromRegisterDTO, userFromRegisterFromInputDTO,
                profileFromRegisterFormInputDTO, addressFromRegisterDTO);

        boolean areDifferentAssociations = areDifferentEins(associationFromRegisterDTO, association);
        boolean areDifferentUsers = areDifferentUsers(user, userFromRegisterFromInputDTO);
        boolean areDifferentProfiles = areDifferentProfiles(user.getProfile(), userFromRegisterFromInputDTO.getProfile());
        boolean isValidPassword = isNotNullAndNotEmpty(userFromRegisterFromInputDTO.getPassword());
        if (isValidPassword || areDifferentUsers || areDifferentProfiles || areDifferentAssociations) {
            if (isValidPassword) {
                user.setPassword(userFromRegisterFromInputDTO.getPassword());
            }
            if (areDifferentUsers) {
                if (areDifferents(user.getEmail(), userFromRegisterFromInputDTO.getEmail())) {
                    validateNonExistingEmail(userFromRegisterFromInputDTO.getEmail());
                }
                updateUserFromDTO(user, userFromRegisterFromInputDTO);
            }
            if (areDifferentProfiles) {
                updateProfileFromDTO(user.getProfile(), profileFromRegisterFormInputDTO);
            }
            if (areDifferentAssociations) {
                user.getProfile().setAssociation(associationFromRegisterDTO);
            }
            userService.saveUser(user);
        }

        boolean areDifferentAddressesCitiesStates = areDifferentAddressesCitiesStates(address, addressFromRegisterDTO);
        if (areDifferentAddressesCitiesStates) {
            updateAddressFromDTO(address, addressFromRegisterDTO);
            addressService.saveAddress(address);
        }
    }

    private void validPermission(Long userId) {
        AuthenticationResponse authenticationResponse = authService.current();
        boolean isAllowedRole = false;
        for (String authority : authenticationResponse.getAuthorities()) {
            if (authority.equals("ROLE_ROOT") || authority.equals("ROLE_ADMIN")) {
                isAllowedRole = true;
                break;
            }
        }
        if (!isAllowedRole && !authenticationResponse.getId().equals(userId)) {
            throw new ForbiddenOperationException("Apenas o usuário desta ficha ou administradores podem acessá-la");
        }
    }

    private boolean areDifferentEins(Association associationFromRegisterDTO, Association association) {
        return (!Objects.equals(associationFromRegisterDTO, association)) ||
                (Objects.nonNull(associationFromRegisterDTO) &&
                        !associationFromRegisterDTO.getEin().equalsIgnoreCase(association.getEin()));
    }

    private boolean areDifferentProfiles(Profile profile1, Profile profile2) {
        return !profile1.equals(profile2);
    }

    private boolean areDifferentUsers(User user, User userFromRegisterFromInputDTO) {
        return !user.equals(userFromRegisterFromInputDTO);
    }

    private boolean areDifferentAddressesCitiesStates(Address address, Address addressFromRegisterDTO) {
        boolean areDifferenteAddresses = !address.equals(addressFromRegisterDTO);
        boolean areDifferenteCities = !address.getCity().equals(addressFromRegisterDTO.getCity());
        boolean areDifferenteStates = !address.getCity().getState().equals(addressFromRegisterDTO.getCity().getState());
        return areDifferenteAddresses || areDifferenteCities || areDifferenteStates;
    }

    private void updateAddressFromDTO(Address address, Address addressFromRegisterDTO) {
        address.setStreet(addressFromRegisterDTO.getStreet());
        address.setNumber(addressFromRegisterDTO.getNumber());
        address.setZipCode(addressFromRegisterDTO.getZipCode());
        address.setNeighbourhood(addressFromRegisterDTO.getNeighbourhood());
        address.setCity(addressFromRegisterDTO.getCity());
    }

    private void updateUserFromDTO(User user, User userFromRegisterFromInputDTO) {
        user.setEmail(userFromRegisterFromInputDTO.getEmail());
        user.setFirstname(userFromRegisterFromInputDTO.getFirstname());
        user.setLastname(userFromRegisterFromInputDTO.getLastname());
        user.setStatus(userFromRegisterFromInputDTO.getStatus());
        user.setRoles(userFromRegisterFromInputDTO.getRoles());
    }

    private void updateProfileFromDTO(Profile profile, Profile profileFromRegisterFromInputDTO) {
        profile.setFullname(profileFromRegisterFromInputDTO.getFullname());
        profile.setFather(profileFromRegisterFromInputDTO.getFather());
        profile.setMother(profileFromRegisterFromInputDTO.getMother());
        profile.setBloodType(profileFromRegisterFromInputDTO.getBloodType());
        profile.setItin(profileFromRegisterFromInputDTO.getItin());
        profile.setNid(profileFromRegisterFromInputDTO.getNid());
        profile.setBirthday(profileFromRegisterFromInputDTO.getBirthday());
        profile.setPhoneNumbers(profileFromRegisterFromInputDTO.getPhoneNumbers()
            .stream().filter(phone -> Objects.nonNull(phone) && !phone.trim().isEmpty()).collect(Collectors.toSet()));
    }


    public void associateAssociationAndUserAndProfileAndAddress(Association association, User user, Profile profile, Address address) {
        profile.setAssociation(association);
        profile.setAddress(address);
        user.setProfile(profile);
        profile.setUser(user);
    }

    public RegistrationFormOutputDTO readRegistrationFormByUserId(Long userId) {
        User user = userService.getUserWithProfileAndGraduations(userId);
        validPermission(userId);
        return RegistrationFormOutputDTO.getRegistrationFormOutputDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<RegistrationFormOutputDTO> getPagedRegistrationForms(String search, String status, Pageable pageable) {
        Page<User> pagedUsers = userService.getPagedUsers(search, status, pageable);
        userService.loadGraduations(pagedUsers.map(User::getProfile));
        return pagedUsers.map(RegistrationFormOutputDTO::getRegistrationFormOutputDTO);
    }

    private boolean isNotNullAndNotEmpty(String word) {
        return Objects.nonNull(word) && !word.trim().isEmpty();
    }

    private boolean areDifferents(String word1, String word2) {
        return isNotNullAndNotEmpty(word1) && isNotNullAndNotEmpty(word2) && !word1.equals(word2);
    }
}
