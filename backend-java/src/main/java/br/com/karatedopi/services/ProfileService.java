package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.GraduationDTO;
import br.com.karatedopi.controllers.dtos.ProfileCreateDTO;
import br.com.karatedopi.controllers.dtos.ProfileReadDTO;
import br.com.karatedopi.controllers.dtos.ProfileUpdateDTO;
import br.com.karatedopi.entities.Graduation;
import br.com.karatedopi.entities.Profile;
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

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final ProfileRepository profileRepository;

	@Transactional(readOnly = true)
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
		foundProfile.setCpf(profileCreateDTO.getCpf());
		foundProfile.setRg(profileCreateDTO.getRg());
		foundProfile.setPhoneNumbers(profileCreateDTO.getPhoneNumbers());
	}

    public ProfileReadDTO changeGraduation(Long id, GraduationDTO graduationDTO) {
		Profile profile = this.getProfile(id);
		Belt newGraduationBelt = Belt.getValueByDescriptionOrValue(graduationDTO.belt());
		Graduation lastGraduation = findLastGraduation(profile.getGraduations());
		validChangeGraduation(lastGraduation, newGraduationBelt);
		Graduation newGraduation = Graduation.builder().belt(newGraduationBelt).profile(profile).build();
		profile.getGraduations().add(newGraduation);
		profile = this.saveProfile(profile);
		return ProfileReadDTO.getProfileReadDTO(profile);
    }

	private void validChangeGraduation(Graduation lastGraduation, Belt graduationBelt) {
		if (Objects.nonNull(lastGraduation)) {
			Integer graduationBeltIndex = Belt.getIndexByValue(graduationBelt);
			Integer lastGraduationBeltIndex = Belt.getIndexByValue(lastGraduation.getBelt());
			if (graduationBeltIndex <= lastGraduationBeltIndex || graduationBeltIndex - lastGraduationBeltIndex > 1) {
				throw new ForbiddenOperationException("Uma nova graduação não pode ser 2 ou mais vezes superior à anterior");
			}
			if (Utils.getDifferenceInHours(lastGraduation.getCreatedOn(), LocalDateTime.now()) < 1) {
				throw new ForbiddenOperationException("Mais de 1 graduação em menos de 1 hora é proibido");
			}
		}
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
