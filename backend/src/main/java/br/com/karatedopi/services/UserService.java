package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.*;
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

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	private final UserRepository userRepository;

	private final AuthenticationService authenticationService;

	@Transactional(readOnly=true)
	public Page<UserReadResponseDTO> findAllPaged(Pageable pageable) {
		Page<User> usersPaged = userRepository.findAll(pageable);
		return usersPaged.map(UserReadResponseDTO::getByUser);
	}

	public UserReadResponseDTO findUser(Long id) {
		User user = getUserById(id);
		return UserReadResponseDTO.getByUser(user);
	}

	public UserCreateResponseDTO createUserAndProfile(RegisterForm registerForm) {
		User user = getUserByRegisterForm(registerForm);
		Profile profile = getProfile(registerForm);
		profile.setUser(user);
		user.setProfile(profile);
		var savedUser = userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		authenticationService.saveUserToken(savedUser, jwtToken);
		return UserCreateResponseDTO.getByUserAndTokens(user, jwtToken, refreshToken);
	}

	public UserUpdateResponseDTO updateUser(Long id, UserInputDTO userInputDTO) {
		User user = getUserById(id);
		user.setId(id);
		user.setEmail(userInputDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userInputDTO.getPassword()));
		var savedUser = userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		authenticationService.saveUserToken(savedUser, jwtToken);
		return UserUpdateResponseDTO.builder()
				.id(savedUser.getId())
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.email(userInputDTO.getEmail())
				.build();
	}

	public void deleteUser(Long id) {
		getUserById(id);
		userRepository.deleteById(id);
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

	private User getUserById(Long id) {
		User foundProfile = userRepository.findById(id).orElse(null);
		if (Objects.isNull(foundProfile)) {
//			TODO: Make a custom exception instead of RuntimeException below
			throw new RuntimeException("Profile not found");
		}
		return foundProfile;
	}

}
