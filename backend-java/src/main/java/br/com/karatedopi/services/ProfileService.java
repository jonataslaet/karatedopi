package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.GraduationDTO;
import br.com.karatedopi.controllers.dtos.ProfileOutputDTO;
import br.com.karatedopi.entities.Graduation;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.ProfileGraduation;
import br.com.karatedopi.entities.enums.Belt;
import br.com.karatedopi.repositories.ProfileRepository;
import br.com.karatedopi.services.exceptions.ForbiddenOperationException;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import br.com.karatedopi.utils.Utils;
import lombok.RequiredArgsConstructor;
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
    public Profile getProfile(Long id) {
        Profile foundProfile = profileRepository.findById(id).orElse(null);
        if (Objects.isNull(foundProfile)) {
            throw new ResourceNotFoundException("Perfil não encontrado com o id " + id);
        }
        return foundProfile;
    }

    public ProfileOutputDTO changeGraduation(Long profileId, GraduationDTO graduationDTO) {
        Profile profile = this.getProfile(profileId);
        Belt newGraduationBelt = Belt.getValueByDescriptionOrValue(graduationDTO.belt());
        loadGraduations(profile);
        ProfileGraduation lastProfileGraduation = findLastProfileGraduation(profile.getProfileGraduations());
        Graduation newGraduation = getValidNewGraduation(lastProfileGraduation, newGraduationBelt);
        graduateProfile(profile, newGraduation);
        return ProfileOutputDTO.getProfileOutputDTO(profile);
    }

    private ProfileGraduation findLastProfileGraduation(Set<ProfileGraduation> profileGraduations) {
        if (!profileGraduations.isEmpty()) {
            return profileGraduations.stream()
                    .max(Comparator.comparing(ProfileGraduation::getCreatedOn))
                    .orElse(null);
        } else {
            return null;
        }
    }

    private void loadGraduations(Profile profile) {
        Set<ProfileGraduation> profileGraduations = profileGraduationService
                .getProfileGraduationsByProfile(profile);
        profile.setProfileGraduations(profileGraduations);
    }

    private Graduation getValidNewGraduation(ProfileGraduation lastProfileGraduation, Belt graduationBelt) {
        if (Objects.nonNull(lastProfileGraduation)) {
            Integer graduationBeltIndex = graduationBelt.getId();
            Integer lastGraduationBeltIndex = lastProfileGraduation.getGraduation().getBelt().getId();
            if (graduationBeltIndex <= lastGraduationBeltIndex) {
                throw new ForbiddenOperationException("A nova graduação deve ser maior que a anterior");
            }
            if (graduationBeltIndex - lastGraduationBeltIndex > 1) {
                throw new ForbiddenOperationException("Uma nova graduação não pode ser 2 ou mais vezes superior à anterior");
            }
            if (Utils.getDifferenceInMinutes(lastProfileGraduation.getCreatedOn(), LocalDateTime.now()) < 1L) {
                throw new ForbiddenOperationException("Mais de 1 graduação em menos de 1 hora é proibido");
            }
        }
        return graduationService.getGraduation(graduationBelt.toString());
    }

    private Set<Graduation> getGraduations(Set<ProfileGraduation> profileGraduationsByProfile) {
        return profileGraduationsByProfile.stream().map(ProfileGraduation::getGraduation).collect(Collectors.toSet());
    }

    public void graduateProfile(Profile profile, Graduation graduation) {
        Set<Graduation> graduationsByProfile = getGraduations(profile.getProfileGraduations());
        if (!graduationsByProfile.contains(graduation)) {
            ProfileGraduation profileGraduation = ProfileGraduation.builder().build();
            profileGraduation.setGraduation(graduation);
            profileGraduation.setProfile(profile);
            profileGraduation = profileGraduationService.saveGraduation(profileGraduation);
            profile.getProfileGraduations().add(profileGraduation);
        }
    }

}
