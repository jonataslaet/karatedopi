package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.GraduationDTO;
import br.com.karatedopi.controllers.dtos.ProfileCreateDTO;
import br.com.karatedopi.controllers.dtos.ProfileReadDTO;
import br.com.karatedopi.controllers.dtos.ProfileUpdateDTO;
import br.com.karatedopi.entities.Graduation;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.ProfileGraduation;
import br.com.karatedopi.entities.enums.Belt;
import br.com.karatedopi.repositories.ProfileRepository;
import br.com.karatedopi.services.exceptions.ForbiddenOperationException;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import br.com.karatedopi.services.exceptions.ResourceStorageException;
import br.com.karatedopi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final ProfileRepository profileRepository;
	private final GraduationService graduationService;
	private final ProfileGraduationService profileGraduationService;

	@Transactional(readOnly = true)
	public Page<ProfileReadDTO> getPagedProfiles(String hometown, Pageable pageable) {
		Page<Profile> profiles;
		if (isBlank(hometown)) {
			profiles = profileRepository.findAll(pageable);
		} else {
			profiles = profileRepository.findAllByHometown(hometown, pageable);
		}
		loadGraduations(profiles);
		return profiles.map(ProfileReadDTO::getProfileReadDTO);
	}

	private void loadGraduations(Profile profile) {
		Set<ProfileGraduation> profileGraduations = profileGraduationService
				.getProfileGraduationsByProfile(profile);
		profile.setProfileGraduations(profileGraduations);
	}

	private void loadGraduations(Page<Profile> profiles) {
		Set<ProfileGraduation> profileGraduations = profileGraduationService
				.getProfileGraduationsByProfiles(profiles.stream().map(profile ->
						profile.getUser().getProfile()).collect(Collectors.toSet()));
		profiles.forEach(profile -> {
			profile.setProfileGraduations(profileGraduations.stream().filter(profileGraduation ->
					profileGraduation.getProfile().equals(profile)).collect(Collectors.toSet()));
		});
	}

	private static boolean isBlank(String hometown) {
		return hometown == null || hometown.isEmpty();
	}

	public ProfileReadDTO getProfileReadResponseDTO(Long id) {
		Profile profile = getProfile(id);
		loadGraduations(profile);
		return ProfileReadDTO.getProfileReadDTO(profile);
	}

	@Transactional(readOnly = true)
	public Profile getProfile(Long id) {
		Profile foundProfile = profileRepository.findById(id).orElse(null);
		if (Objects.isNull(foundProfile)) {
			throw new ResourceNotFoundException("Perfil não encontrado com o id " + id);
		}
		return foundProfile;
	}

	@Transactional
	public ProfileUpdateDTO updateProfile(Long id, ProfileCreateDTO profileCreateDTO) {
		Profile foundProfile = getProfile(id);
		try {
			fillProfileFromProfileDTO(foundProfile, profileCreateDTO);
			Profile updatedProfile = profileRepository.save(foundProfile);
			return ProfileUpdateDTO.getProfileUpdateResponseDTO(updatedProfile);
		} catch (Exception e) {
			throw new ResourceStorageException("Problema desconhecido ao salvar perfil");
		}
	}

	private void fillProfileFromProfileDTO(Profile foundProfile, ProfileCreateDTO profileCreateDTO) {
		foundProfile.setFullname(profileCreateDTO.getFullname());
		foundProfile.setMother(profileCreateDTO.getMother());
		foundProfile.setFather(profileCreateDTO.getFather());
		foundProfile.setBloodType(profileCreateDTO.getBloodType());
		foundProfile.setBirthday(profileCreateDTO.getBirthday());
		foundProfile.setItin(profileCreateDTO.getItin());
		foundProfile.setNid(profileCreateDTO.getNid());
		foundProfile.setPhoneNumbers(profileCreateDTO.getPhoneNumbers());
	}

    public ProfileReadDTO changeGraduation(Long id, GraduationDTO graduationDTO) {
		Profile profile = this.getProfile(id);
		Belt newGraduationBelt = Belt.getValueByDescriptionOrValue(graduationDTO.belt());
		loadGraduations(profile);
		Graduation newGraduation = getValidNewGraduation(findLastGraduation(getGraduations(profile.getProfileGraduations())), newGraduationBelt);
		graduateProfile(profile, newGraduation);
		profile = this.saveProfile(profile);
		return ProfileReadDTO.getProfileReadDTO(profile);
    }

	private void graduateProfile(Profile profile, Graduation graduation) {
		Set<Graduation> graduationsByProfile = getGraduations(profile.getProfileGraduations());
		if (!graduationsByProfile.contains(graduation)) {
			ProfileGraduation profileGraduation = ProfileGraduation.builder().build();
			profileGraduation.setGraduation(graduation);
			profileGraduation.setProfile(profile);
			profileGraduationService.saveGraduation(profileGraduation);
		}
	}

	private Set<Graduation> getGraduations(Set<ProfileGraduation> profileGraduationsByProfile) {
		return profileGraduationsByProfile.stream().map(ProfileGraduation::getGraduation).collect(Collectors.toSet());
	}

	private Graduation getValidNewGraduation(Graduation lastGraduation, Belt graduationBelt) {
		if (Objects.nonNull(lastGraduation)) {
			Integer graduationBeltIndex = graduationBelt.getId();
			Integer lastGraduationBeltIndex = lastGraduation.getBelt().getId();
			if (graduationBeltIndex <= lastGraduationBeltIndex || graduationBeltIndex - lastGraduationBeltIndex > 1) {
				throw new ForbiddenOperationException("Uma nova graduação não pode ser 2 ou mais vezes superior à anterior");
			}
			if (Utils.getDifferenceInHours(lastGraduation.getCreatedOn(), LocalDateTime.now()) < 1) {
				throw new ForbiddenOperationException("Mais de 1 graduação em menos de 1 hora é proibido");
			}
		}
		return graduationService.getGraduation(graduationBelt.toString());
	}

	private Graduation findLastGraduation(Set<Graduation> graduations) {
		if (!graduations.isEmpty()) {
			return graduations.stream()
					.max(Comparator.comparing(Graduation::getCreatedOn))
					.orElse(null);
		} else {
			return null;
		}
	}

	@Transactional
	public Profile saveProfile(Profile profile) {
		try {
			return profileRepository.save(profile);
		} catch (Exception e) {
			throw new ResourceStorageException("Problema desconhecido ao salvar perfil");
		}
	}
}
