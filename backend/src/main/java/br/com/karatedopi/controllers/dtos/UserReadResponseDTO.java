package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReadResponseDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;

    public static UserReadResponseDTO getByUser(User user) {
        return UserReadResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getProfile().getFirstname())
                .lastname(user.getProfile().getLastname())
                .build();
    }
}
