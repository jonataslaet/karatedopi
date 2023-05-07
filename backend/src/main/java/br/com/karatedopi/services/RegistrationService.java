package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.RegisterForm;
import br.com.karatedopi.controllers.dtos.UserRegistrationResponseDTO;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.enums.Role;
import br.com.karatedopi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegistrationService {

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	private final UserRepository userRepository;

	private final AuthenticationService authenticationService;

	public UserRegistrationResponseDTO createRegistration(RegisterForm registerForm) {
		User user = getUserByRegisterForm(registerForm);
		Profile profile = getProfile(registerForm);
		profile.setUser(user);
		user.setProfile(profile);
		var savedUser = userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		authenticationService.saveUserToken(savedUser, jwtToken);
		return UserRegistrationResponseDTO.getByUserAndTokens(user, jwtToken, refreshToken);
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

	private User getUserByRegisterForm(RegisterForm registerForm) {
		return User.builder()
				.email(registerForm.getEmail())
				.password(passwordEncoder.encode(registerForm.getPassword()))
				.role(Role.USER)
				.build();
	}

	private static Profile getProfile(RegisterForm registerForm) {
		return Profile.builder()
				.fullname(registerForm.getFullname())
				.mother(registerForm.getMother())
				.father(registerForm.getFather())
				.zipCode(registerForm.getZipCode())
				.street(registerForm.getStreet())
				.number(registerForm.getNumber())
				.neighbourhood(registerForm.getNeighbourhood())
				.city(registerForm.getCity())
				.state(registerForm.getState())
				.bloodType(registerForm.getBloodType())
				.birthday(registerForm.getBirthday())
				.cpf(registerForm.getCpf())
				.rg(registerForm.getRg())
				.phoneNumbers(registerForm.getPhoneNumbers())
				.createdOn(registerForm.getCreatedOn())
				.updatedOn(registerForm.getUpdatedOn())
				.build();
	}

}
