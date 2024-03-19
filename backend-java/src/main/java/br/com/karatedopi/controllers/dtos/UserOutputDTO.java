package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.enums.UserStatus;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(allowGetters = true)
public class UserOutputDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String authority;
    private String email;
    private UserStatus status;

    public static UserOutputDTO getUserOutputDTO(User user) {
        return UserOutputDTO.builder()
                .id(user.getId())
                .authority(user.getRoles().stream().map(Role::getAuthority).findFirst().orElseThrow(() ->
                        new ResourceNotFoundException("Autoridade não encontrada")))
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .status(user.getStatus())
                .build();
    }
}
