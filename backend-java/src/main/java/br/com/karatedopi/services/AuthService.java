package br.com.karatedopi.services;

import br.com.karatedopi.configurations.TokenConfiguration;
import br.com.karatedopi.controllers.dtos.AuthenticationResponse;
import br.com.karatedopi.controllers.dtos.CredentialsDTO;
import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.repositories.UserRepository;
import br.com.karatedopi.services.exceptions.InvalidAuthenticationException;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfiguration tokenConfiguration;
    private final HttpServletRequest httpServletRequest;

    public AuthenticationResponse login(CredentialsDTO credentialsDTO) {
        User user = userRepository.findByEmail(credentialsDTO.email())
                .orElseThrow(() -> new ResourceNotFoundException("Unknown user"));
        if (isMatchedPassword(credentialsDTO, user.getPassword())) {
            return AuthenticationResponse.builder()
                    .id(user.getId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(credentialsDTO.email())
                    .accessToken(tokenConfiguration.createToken(user))
                    .authorities(user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet()))
                    .build();
        }
        throw new InvalidAuthenticationException("Email or password are invalid");
    }

    public AuthenticationResponse current() {
        User user = authenticated();
        if (Objects.isNull(user)) return null;
        return AuthenticationResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .accessToken(tokenConfiguration.getCurrentToken(httpServletRequest))
                .authorities(user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet()))
                .build();
    }

    public static User authenticated() {
        try {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isMatchedPassword(CredentialsDTO credentialsDTO, String encodedPassword) {
        return passwordEncoder.matches(credentialsDTO.password(), encodedPassword);
    }
}
