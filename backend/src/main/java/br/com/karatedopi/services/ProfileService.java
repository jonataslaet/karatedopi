package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.ProfileDTO;
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

	public Page<ProfileDTO> getPagedProfiles(String hometown, Pageable paginacao) {
		Page<Profile> profiles;
		if (hometown == null || hometown.isEmpty()) {
			profiles = profileRepository.findAll(paginacao);
		} else {
			profiles = profileRepository.findAllByHometown(hometown, paginacao);
		}

		return profiles.map(profile -> getProfileDTO(profile));
	}

	private static ProfileDTO getProfileDTO(Profile profile) {
		return ProfileDTO.builder()
				.id(profile.getId())
				.rg(profile.getRg())
				.cpf(profile.getCpf())
				.firstname(profile.getFirstname())
				.lastname(profile.getLastname())
				.fullname(profile.getFullname())
				.father(profile.getFather())
				.mother(profile.getMother())
				.phoneNumbers(profile.getPhoneNumbers())
				.hometown(profile.getHometown())
				.birthday(profile.getBirthday())
				.creationDate(profile.getCreatedOn())
				.lastUpdate(profile.getUpdatedOn())
				.build();
	}

	public ProfileDTO getProfileDTO(Long id) {
		Profile profileFound = getProfile(id);
		return getProfileDTO(profileFound);
	}

	private Profile getProfile(Long id) {
		Profile foundProfile = profileRepository.findById(id).orElse(null);
		if (Objects.isNull(foundProfile)) {
//			TODO: Make a custom exception instead of RuntimeException below
			throw new RuntimeException("Profile not found");
		}
		return foundProfile;
	}

	public ProfileDTO updateProfile(Long id, ProfileDTO profileDTO) {
		Profile foundProfile = getProfile(id);
		fillProfileFromProfileDTO(foundProfile, profileDTO);
		Profile updatedProfile = profileRepository.save(foundProfile);
		return getProfileDTO(updatedProfile);
	}

	private void fillProfileFromProfileDTO(Profile foundProfile, ProfileDTO profileDTO) {
		foundProfile.setHometown(profileDTO.getHometown());
		foundProfile.setRg(profileDTO.getRg());
		foundProfile.setMother(profileDTO.getMother());
		foundProfile.setFather(profileDTO.getFather());
		foundProfile.setPhoneNumbers(profileDTO.getPhoneNumbers());
		foundProfile.setFullname(profileDTO.getFullname());
		foundProfile.setCpf(profileDTO.getCpf());
		foundProfile.setFirstname(profileDTO.getFirstname());
		foundProfile.setLastname(profileDTO.getLastname());
		foundProfile.setBirthday(profileDTO.getBirthday());
	}

	public void deleteProfile(Long id) {
		getProfile(id);
		profileRepository.deleteById(id);
	}
}
