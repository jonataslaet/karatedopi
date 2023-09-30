package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.ProfileCreateDTO;
import br.com.karatedopi.controllers.dtos.ProfileReadDTO;
import br.com.karatedopi.controllers.dtos.ProfileUpdateDTO;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final ProfileRepository profileRepository;

	public Page<ProfileReadDTO> getPagedProfiles(String hometown, Pageable paginacao) {
		Page<Profile> profiles;
		if (isBlank(hometown)) {
			profiles = profileRepository.findAll(paginacao);
		} else {
			profiles = profileRepository.findAllByHometown(hometown, paginacao);
		}

		return profiles.map(ProfileReadDTO::getProfileReadDTO);
	}

	private static boolean isBlank(String hometown) {
		return hometown == null || hometown.isEmpty();
	}

	public ProfileReadDTO getProfileReadResponseDTO(Long id) {
		Profile profile = getProfile(id);
		return ProfileReadDTO.getProfileReadDTO(profile);
	}

	private Profile getProfile(Long id) {
		Profile foundProfile = profileRepository.findById(id).orElse(null);
		if (Objects.isNull(foundProfile)) {
//			TODO: Make a custom exception instead of RuntimeException below
			throw new RuntimeException("Profile not found");
		}
		return foundProfile;
	}

	public ProfileUpdateDTO updateProfile(Long id, ProfileCreateDTO profileCreateDTO) {
		Profile foundProfile = getProfile(id);
		fillProfileFromProfileDTO(foundProfile, profileCreateDTO);
		Profile updatedProfile = profileRepository.save(foundProfile);
		return ProfileUpdateDTO.getProfileUpdateResponseDTO(updatedProfile);
	}

	private void fillProfileFromProfileDTO(Profile foundProfile, ProfileCreateDTO profileCreateDTO) {
		foundProfile.setFullname(profileCreateDTO.getFullname());
		foundProfile.setMother(profileCreateDTO.getMother());
		foundProfile.setFather(profileCreateDTO.getFather());
		foundProfile.setZipCode(profileCreateDTO.getZipCode());
		foundProfile.setStreet(profileCreateDTO.getStreet());
		foundProfile.setNumber(profileCreateDTO.getNumber());
		foundProfile.setNeighbourhood(profileCreateDTO.getNeighbourhood());
		foundProfile.setCity(profileCreateDTO.getCity());
		foundProfile.setState(profileCreateDTO.getState());
		foundProfile.setBloodType(profileCreateDTO.getBloodType());
		foundProfile.setBirthday(profileCreateDTO.getBirthday());
		foundProfile.setCpf(profileCreateDTO.getCpf());
		foundProfile.setRg(profileCreateDTO.getRg());
		foundProfile.setPhoneNumbers(profileCreateDTO.getPhoneNumbers());
	}
}
