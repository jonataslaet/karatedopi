package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.RegisterDTO;
import br.com.karatedopi.entities.*;
import br.com.karatedopi.entities.enums.UserStatus;
import br.com.karatedopi.repositories.AddressRepository;
import br.com.karatedopi.repositories.RoleRepository;
import br.com.karatedopi.repositories.UserRepository;
import br.com.karatedopi.services.exceptions.AlreadyInUseException;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class RegistrationService {
	private static final Long ROLE_USER_ID = 4L;

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final StateService stateService;
	private final AddressRepository addressRepository;

	@Transactional
	public RegisterDTO createRegistration(RegisterDTO registerDTO) {
		validateNonExistingEmail(registerDTO.getEmail());
		User user = getUser(registerDTO);
		Profile profile = getProfile(registerDTO);
		user.setFirstname(getFirstname(profile.getFullname()));
		user.setLastname(getLastname(profile.getFullname()));
		profile.setUser(user);
		user.setProfile(profile);
		Address address = profile.getAddress();
		address.getResidents().add(profile);
		addressRepository.save(address);
		User savedUser = userRepository.save(user);
		registerDTO.setId(savedUser.getId());
		return registerDTO;
	}

	private void validateNonExistingEmail(String email) {
		if (userRepository.countUsersByEmail(email) > 0) {
			throw new AlreadyInUseException("Email already exists");
		}
	}

	@Transactional
	public void deleteRegistrationByUserId(Long userId) {
		validUserExistence(userId);
		userRepository.deleteById(userId);
	}

	private void validUserExistence(Long id) {
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("User not found for id = " + id);
		}
	}

	private User getUser(RegisterDTO registerDTO) {
		User user = User.builder()
				.email(registerDTO.getEmail())
				.password(passwordEncoder.encode(registerDTO.getPassword()))
				.status(UserStatus.PENDING_EVALUATION)
				.roles(new HashSet<>())
				.build();
		roleRepository.findById(ROLE_USER_ID).ifPresent(role -> user.getRoles().add(role));
		return user;
	}

	private Profile getProfile(RegisterDTO registerDTO) {
		return Profile.builder()
				.fullname(registerDTO.getFullname())
				.mother(registerDTO.getMother())
				.father(registerDTO.getFather())
				.address(getAddress(registerDTO))
				.bloodType(registerDTO.getBloodType())
				.birthday(registerDTO.getBirthday())
				.cpf(registerDTO.getCpf())
				.rg(registerDTO.getRg())
				.phoneNumbers(registerDTO.getPhoneNumbers())
				.createdOn(registerDTO.getCreatedOn())
				.updatedOn(registerDTO.getUpdatedOn())
				.build();
	}

	private Address getAddress(RegisterDTO registerDTO) {
		State state = stateService.findStateByName(registerDTO.getState());
		City city = getCityByName(registerDTO.getCity(), state);
		return Address.builder()
				.street(registerDTO.getStreet())
				.number(registerDTO.getNumber())
				.zipCode(registerDTO.getZipCode())
				.neighbourhood(registerDTO.getNeighbourhood())
				.city(city)
				.build();
	}

	private City getCityByName(String cityName, State state) {
		return state.getCities().stream().filter(city ->
			cityName.equalsIgnoreCase(city.getName())).findFirst().orElseThrow(() ->
				new ResourceNotFoundException("No city was found for this state"));
	}

	private String getFirstname(String fullname) {
		String name = getPiecesOfFullname(fullname)[0];
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	private String getLastname(String fullname) {
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

}
