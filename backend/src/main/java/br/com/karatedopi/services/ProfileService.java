package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.ProfileInputDTO;
import br.com.karatedopi.controllers.dtos.ProfileReadResponseDTO;
import br.com.karatedopi.controllers.dtos.ProfileUpdateResponseDTO;
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

	public Page<ProfileReadResponseDTO> getPagedProfiles(String hometown, Pageable paginacao) {
		Page<Profile> profiles;
		if (hometown == null || hometown.isEmpty()) {
			profiles = profileRepository.findAll(paginacao);
		} else {
			profiles = profileRepository.findAllByHometown(hometown, paginacao);
		}

		return profiles.map(ProfileReadResponseDTO::getProfileReadResponseDTO);
	}

	public ProfileReadResponseDTO getProfileReadResponseDTO(Long id) {
		Profile profile = getProfile(id);
		return ProfileReadResponseDTO.getProfileReadResponseDTO(profile);
	}

	private Profile getProfile(Long id) {
		Profile foundProfile = profileRepository.findById(id).orElse(null);
		if (Objects.isNull(foundProfile)) {
//			TODO: Make a custom exception instead of RuntimeException below
			throw new RuntimeException("Profile not found");
		}
		return foundProfile;
	}

	public ProfileUpdateResponseDTO updateProfile(Long id, ProfileInputDTO profileInputDTO) {
		Profile foundProfile = getProfile(id);
		fillProfileFromProfileDTO(foundProfile, profileInputDTO);
		Profile updatedProfile = profileRepository.save(foundProfile);
		return ProfileUpdateResponseDTO.getProfileUpdateResponseDTO(updatedProfile);
	}

	private void fillProfileFromProfileDTO(Profile foundProfile, ProfileInputDTO profileInputDTO) {
		foundProfile.setFullname(profileInputDTO.getFullname());
		foundProfile.setMother(profileInputDTO.getMother());
		foundProfile.setFather(profileInputDTO.getFather());
		foundProfile.setZipCode(profileInputDTO.getZipCode());
		foundProfile.setStreet(profileInputDTO.getStreet());
		foundProfile.setNumber(profileInputDTO.getNumber());
		foundProfile.setNeighbourhood(profileInputDTO.getNeighbourhood());
		foundProfile.setCity(profileInputDTO.getCity());
		foundProfile.setState(profileInputDTO.getState());
		foundProfile.setBloodType(profileInputDTO.getBloodType());
		foundProfile.setBirthday(profileInputDTO.getBirthday());
		foundProfile.setCpf(profileInputDTO.getCpf());
		foundProfile.setRg(profileInputDTO.getRg());
		foundProfile.setPhoneNumbers(profileInputDTO.getPhoneNumbers());
	}
}
