package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.RegisterForm;
import br.com.karatedopi.controllers.dtos.UserDTO;
import br.com.karatedopi.controllers.dtos.UserRegistrationResponse;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.enums.Role;
import br.com.karatedopi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	private final UserRepository userRepository;

	private final AuthenticationService authenticationService;

	@Transactional(readOnly=true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> usersPaged = userRepository.findAll(pageable);
		return usersPaged.map(userDTO -> UserDTO.builder()
				.email(userDTO.getEmail())
				.build());
	}

	public UserRegistrationResponse register(RegisterForm registerForm) {
		User user = getUser(registerForm);
		Profile profile = getProfile(registerForm);
		profile.setUser(user);
		user.setProfile(profile);
		var savedUser = userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		authenticationService.saveUserToken(savedUser, jwtToken);
		return UserRegistrationResponse.builder()
				.id(savedUser.getId())
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.firstAndLastName(profile.getFirstname() + " " + profile.getLastname())
				.build();
	}

	private User getUser(RegisterForm registerForm) {
		return User.builder()
				.email(registerForm.getEmail())
				.password(passwordEncoder.encode(registerForm.getPassword()))
				.role(Role.USER)
				.build();
	}

	private static Profile getProfile(RegisterForm registerForm) {
		return Profile.builder()
				.cpf(registerForm.getCpf())
				.rg(registerForm.getRg())
				.firstname(registerForm.getFirstname())
				.lastname(registerForm.getLastname())
				.fullname(registerForm.getFullname())
				.mother(registerForm.getMother())
				.father(registerForm.getFather())
				.hometown(registerForm.getHometown())
				.birthday(registerForm.getBirthday())
				.phoneNumbers(registerForm.getPhoneNumbers())
				.createdOn(registerForm.getCreatedOn())
				.updatedOn(registerForm.getUpdatedOn())
				.build();
	}


}
