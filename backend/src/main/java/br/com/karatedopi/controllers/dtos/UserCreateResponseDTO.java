package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateResponseDTO {

    private Long id;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("firstAndLastName")
    private String firstAndLastName;

    public static UserCreateResponseDTO getByUserAndTokens(User user, String jwtToken, String refreshToken) {
        return UserCreateResponseDTO.builder()
                .id(user.getId())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .firstAndLastName(user.getProfile().getFirstname() + " " + user.getProfile().getLastname())
                .build();
    }
}
