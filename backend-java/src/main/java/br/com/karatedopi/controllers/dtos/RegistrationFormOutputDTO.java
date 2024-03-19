package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.Association;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationFormOutputDTO {

    @JsonProperty("user")
    private UserOutputDTO userOutputDTO;

    @JsonProperty("profile")
    private ProfileOutputDTO profileOutputDTO;

    @JsonProperty("address")
    private AddressDTO addressDTO;

    public static RegistrationFormOutputDTO getRegistrationFormOutputDTO(User user) {
        Profile profile = user.getProfile();
        Address address = profile.getAddress();
        return RegistrationFormOutputDTO.builder()
                .userOutputDTO(UserOutputDTO.getUserOutputDTO(user))
                .profileOutputDTO(ProfileOutputDTO.getProfileOutputDTO(profile))
                .addressDTO(AddressDTO.getAddressDTO(address))
                .build();
    }
}
