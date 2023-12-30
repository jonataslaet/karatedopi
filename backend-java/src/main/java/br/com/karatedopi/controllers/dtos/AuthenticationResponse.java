package br.com.karatedopi.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String accessToken;

    @Builder.Default
    private Set<String> authorities = new HashSet<>();
}
