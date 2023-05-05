package br.com.karatedopi.controllers.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInputDTO {
    private String email;
    private String password;
}
