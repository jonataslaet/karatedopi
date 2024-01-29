package br.com.karatedopi.services;

import br.com.karatedopi.entities.Graduation;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.ProfileGraduation;
import br.com.karatedopi.repositories.ProfileGraduationRepository;
import br.com.karatedopi.services.exceptions.ResourceStorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileGraduationService {

	private final ProfileGraduationRepository profileGraduationRepository;

	@Transactional(readOnly = true)
	public Graduation getGraduationByProfileAndBelt(Profile profile, String belt) {
		ProfileGraduation profileGraduationByProfileAndBelt =
				profileGraduationRepository.findProfileGraduationByProfileAndBelt(profile, belt);
		return profileGraduationByProfileAndBelt.getGraduation();
	}

	@Transactional(readOnly = true)
	public Set<Graduation> getGraduationsByProfile(Profile profile) {
		Set<ProfileGraduation> profileGraduationsByProfile =
				this.getProfileGraduationsByProfile(profile);
		return profileGraduationsByProfile.stream().map(ProfileGraduation::getGraduation).collect(Collectors.toSet());
	}

	@Transactional(readOnly = true)
	public Set<Graduation> getGraduationsByBelt(String belt) {
		Set<ProfileGraduation> profileGraduationsByProfile =
				profileGraduationRepository.findProfileGraduationsByBelt(belt);
		return profileGraduationsByProfile.stream().map(ProfileGraduation::getGraduation).collect(Collectors.toSet());
	}

	@Transactional
	public void saveGraduation(ProfileGraduation profileGraduation) {
		try {
			profileGraduationRepository.save(profileGraduation);
		} catch (Exception e) {
			throw new ResourceStorageException("Problema desconhecido ao salvar graduação");
		}
	}

	public Set<ProfileGraduation> getProfileGraduationsByBelt(String belt) {
		return new HashSet<>(profileGraduationRepository.findProfileGraduationsByBelt(belt));
	}

	@Transactional(readOnly = true)
	public Set<ProfileGraduation> getProfileGraduationsByProfile(Profile profile) {
		return profileGraduationRepository.findProfileGraduationsByProfile(profile);
	}

	public Set<ProfileGraduation> getProfileGraduationsByProfiles(Set<Profile> profiles) {
		return profileGraduationRepository.findProfileGraduationsByProfiles(profiles);
	}
}

