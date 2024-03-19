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
    public Set<ProfileGraduation> getProfileGraduationsByProfiles(Set<Profile> profiles) {
        return profileGraduationRepository.findProfileGraduationsByProfiles(profiles);
    }

    @Transactional(readOnly = true)
    public Set<ProfileGraduation> getProfileGraduationsByProfile(Profile profile) {
        return profileGraduationRepository.findProfileGraduationsByProfile(profile);
    }

    @Transactional
    public ProfileGraduation saveGraduation(ProfileGraduation profileGraduation) {
        try {
            return profileGraduationRepository.save(profileGraduation);
        } catch (Exception e) {
            throw new ResourceStorageException("Problema desconhecido ao salvar graduação");
        }
    }
}
