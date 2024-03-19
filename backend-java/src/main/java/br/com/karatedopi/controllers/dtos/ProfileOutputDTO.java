package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Graduation;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.ProfileGraduation;
import br.com.karatedopi.entities.enums.Belt;
import br.com.karatedopi.services.exceptions.ForbiddenOperationException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(allowGetters = true)
public class ProfileOutputDTO {

    private String fullname;
    private String father;
    private String mother;
    private String bloodType;
    private String itin;
    private String nid;
    private String associationAbbreviation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    @Builder.Default
    private LocalDateTime lastUpdate = LocalDateTime.now();

    @Builder.Default
    private Set<String> phoneNumbers = new HashSet<>();

    private Belt currentBelt;

    public static ProfileOutputDTO getProfileOutputDTO(Profile profile) {
        Set<ProfileGraduation> profileGraduations = profile.getProfileGraduations();
        return ProfileOutputDTO.builder()
                .fullname(profile.getFullname())
                .mother(profile.getMother())
                .father(profile.getFather())
                .bloodType(profile.getBloodType())
                .birthday(profile.getBirthday())
                .itin(profile.getItin())
                .nid(profile.getNid())
                .phoneNumbers(profile.getPhoneNumbers())
                .associationAbbreviation(Objects.isNull(profile.getAssociation()) ?
                        null : profile.getAssociation().getAssociationAbbreviation())
                .currentBelt(profileGraduations.stream()
                        .max(Comparator.comparing(ProfileGraduation::getCreatedOn))
                        .map(ProfileGraduation::getGraduation).map(Graduation::getBelt)
                        .orElse(null))
                .creationDate(profile.getCreatedOn())
                .lastUpdate(profile.getUpdatedOn())
                .build();
    }

    public void setBirthday(String birthday) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            this.birthday = LocalDate.parse(birthday, formatter);
        } catch (DateTimeException e) {
            throw new ForbiddenOperationException(e.getLocalizedMessage());
        }
    }

}
