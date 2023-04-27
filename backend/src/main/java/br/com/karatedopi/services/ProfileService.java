package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.ProfileDTO;
import br.com.karatedopi.controllers.dtos.ProfileRegistrationResponse;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.repositories.ProfileRepository;
import br.com.karatedopi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final ProfileRepository profileRepository;

	private final UserRepository userRepository;

	public ProfileRegistrationResponse updateProfile(Long userId, ProfileDTO profileDTO) {
		User user = getUser(userId);

		Profile profile = user.getProfile();

		profile.setRg(profileDTO.getRg());
		profile.setCpf(profileDTO.getCpf());
		profile.setFirstname(profileDTO.getFirstname());
		profile.setLastname(profileDTO.getLastname());
		profile.setFullname(profileDTO.getFullname());
		profile.setFather(profileDTO.getFather());
		profile.setMother(profileDTO.getMother());
		profile.setPhoneNumbers(profileDTO.getPhoneNumbers());
		profile.setHometown(profileDTO.getHometown());
		profile.setUser(user);
		user.setProfile(profile);

		userRepository.save(user);

		return ProfileRegistrationResponse.builder()
				.rg(profileDTO.getRg())
				.cpf(profileDTO.getCpf())
				.firstname(profileDTO.getFirstname())
				.lastname(profileDTO.getLastname())
				.fullname(profileDTO.getFullname())
				.father(profileDTO.getFather())
				.mother(profileDTO.getMother())
				.phoneNumbers(profileDTO.getPhoneNumbers())
				.registrationDate(profileDTO.getRegistrationDate())
				.hometown(profileDTO.getHometown())
				.build();
	}

	public User getUser(Long userId) {
		User userLogado = userRepository.findById(userId).orElse(null);
		if (userLogado == null) {
			throw new RuntimeException("Usuário está deslogado");
		}

		return userLogado;
	}

	public Page<ProfileDTO> getPagedProfiles(String hometown, Pageable paginacao) {
		Page<Profile> profiles = null;
		if (hometown == null || hometown.isEmpty()) {
			profiles = profileRepository.findAll(paginacao);
		} else {
			profiles = profileRepository.findAllByHometown(hometown, paginacao);
		}
		return profiles.map(profileDTO -> ProfileDTO.builder()
				.rg(profileDTO.getRg())
				.cpf(profileDTO.getCpf())
				.firstname(profileDTO.getFirstname())
				.lastname(profileDTO.getLastname())
				.fullname(profileDTO.getFullname())
				.father(profileDTO.getFather())
				.mother(profileDTO.getMother())
				.phoneNumbers(profileDTO.getPhoneNumbers())
				.hometown(profileDTO.getHometown())
				.build());
	}
}
