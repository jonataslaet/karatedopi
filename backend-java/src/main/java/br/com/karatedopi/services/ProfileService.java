package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.ProfileCreateDTO;
import br.com.karatedopi.controllers.dtos.ProfileReadDTO;
import br.com.karatedopi.controllers.dtos.ProfileUpdateDTO;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.repositories.ProfileRepository;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final ProfileRepository profileRepository;

	public Page<ProfileReadDTO> getPagedProfiles(String hometown, Pageable pageable) {
		Page<Profile> profiles;
		if (isBlank(hometown)) {
			profiles = profileRepository.findAll(pageable);
		} else {
			profiles = profileRepository.findAllByHometown(hometown, pageable);
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

	@Transactional(readOnly = true)
	public Profile getProfile(Long id) {
		Profile foundProfile = profileRepository.findById(id).orElse(null);
		if (Objects.isNull(foundProfile)) {
			throw new ResourceNotFoundException("Profile not found id = " + id);
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
		foundProfile.setBloodType(profileCreateDTO.getBloodType());
		foundProfile.setBirthday(profileCreateDTO.getBirthday());
		foundProfile.setCpf(profileCreateDTO.getCpf());
		foundProfile.setRg(profileCreateDTO.getRg());
		foundProfile.setPhoneNumbers(profileCreateDTO.getPhoneNumbers());
	}
}
