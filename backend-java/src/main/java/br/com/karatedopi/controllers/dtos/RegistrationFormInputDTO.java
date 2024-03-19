package br.com.karatedopi.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationFormInputDTO {

    @JsonProperty("user")
    private UserInputDTO userInputDTO;

    @JsonProperty("profile")
    private ProfileInputDTO profileInputDTO;

    @JsonProperty("address")
    private AddressDTO addressDTO;
}
