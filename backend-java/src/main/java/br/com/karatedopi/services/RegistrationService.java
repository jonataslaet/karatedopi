package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.RegisterDTO;
import br.com.karatedopi.entities.*;
import br.com.karatedopi.repositories.AddressRepository;
import br.com.karatedopi.repositories.RoleRepository;
import br.com.karatedopi.repositories.UserRepository;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegistrationService {
	private static final Long ROLE_USER_ID = 4L;

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final StateService stateService;

	private final AddressRepository addressRepository;

	public RegisterDTO createRegistration(RegisterDTO registerDTO) {
		User user = getUser(registerDTO);
		Profile profile = getProfile(registerDTO);
		profile.setUser(user);
		user.setProfile(profile);
		Address address = profile.getAddress();
		address.getResidents().add(profile);
		addressRepository.save(address);
		User savedUser = userRepository.save(user);
		registerDTO.setId(savedUser.getId());
		return registerDTO;
	}

	public void deleteRegistrationByUserId(Long userId) {
		getUserById(userId);
		userRepository.deleteById(userId);
	}

	private User getUserById(Long id) {
		User foundProfile = userRepository.findById(id).orElse(null);
		if (Objects.isNull(foundProfile)) {
//			TODO: Make a custom exception instead of RuntimeException below
			throw new RuntimeException("User not found");
		}
		return foundProfile;
	}

	private User getUser(RegisterDTO registerDTO) {
		User user = User.builder()
				.email(registerDTO.getEmail())
				.password(passwordEncoder.encode(registerDTO.getPassword()))
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
}
