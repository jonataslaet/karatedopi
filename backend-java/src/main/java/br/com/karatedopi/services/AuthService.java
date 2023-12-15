package br.com.karatedopi.services;

import br.com.karatedopi.configurations.TokenConfiguration;
import br.com.karatedopi.controllers.dtos.AuthenticationResponse;
import br.com.karatedopi.controllers.dtos.CredentialsDTO;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.repositories.UserRepository;
import br.com.karatedopi.services.exceptions.InvalidAuthenticationException;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfiguration tokenConfiguration;

    public AuthenticationResponse login(CredentialsDTO credentialsDTO) {
        User user = userRepository.findByEmail(credentialsDTO.email())
                .orElseThrow(() -> new ResourceNotFoundException("Unknown user"));
        if (isMatchedPassword(credentialsDTO, user.getPassword())) {
            return AuthenticationResponse.builder()
                    .email(credentialsDTO.email())
                    .accessToken(tokenConfiguration.createToken(credentialsDTO))
                    .build();
        }
        throw new InvalidAuthenticationException("Email or password are invalid");
    }

    private boolean isMatchedPassword(CredentialsDTO credentialsDTO, String encodedPassword) {
        return passwordEncoder.matches(credentialsDTO.password(), encodedPassword);
    }
}
