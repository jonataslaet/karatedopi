package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.ProfileDTO;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

		return profiles.map(profile -> ProfileDTO.builder()
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
				.build());
	}
}
