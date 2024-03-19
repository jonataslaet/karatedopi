package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.services.exceptions.ForbiddenOperationException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(allowSetters = true)
public class ProfileInputDTO {

    private String fullname;
    private String father;
    private String mother;
    private String bloodType;
    private String itin;
    private String nid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @JsonProperty("associationAbbreviation")
    private String associationAbbreviation;

    @Builder.Default
    private Set<String> phoneNumbers = new HashSet<>();

    public static ProfileInputDTO getProfileInputDTO(Profile profile) {
        return ProfileInputDTO.builder()
                .fullname(profile.getFullname())
                .mother(profile.getMother())
                .father(profile.getFather())
                .bloodType(profile.getBloodType())
                .birthday(profile.getBirthday())
                .itin(profile.getItin())
                .nid(profile.getNid())
                .phoneNumbers(profile.getPhoneNumbers())
                .build();
    }

    public void setBirthday(String birthday) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.birthday = LocalDate.parse(birthday, formatter);
        } catch (DateTimeException e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            this.birthday = LocalDate.parse(birthday, formatter);
        } catch (Exception e) {
            throw new ForbiddenOperationException(e.getLocalizedMessage());
        }
    }

}
