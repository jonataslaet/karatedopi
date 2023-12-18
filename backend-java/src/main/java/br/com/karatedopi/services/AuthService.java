package br.com.karatedopi.services;

import br.com.karatedopi.configurations.TokenConfiguration;
import br.com.karatedopi.controllers.dtos.AuthenticationResponse;
import br.com.karatedopi.controllers.dtos.CredentialsDTO;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.repositories.ProfileRepository;
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
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfiguration tokenConfiguration;
    private final HttpServletRequest httpServletRequest;

    public AuthenticationResponse login(CredentialsDTO credentialsDTO) {
        User user = userRepository.findByEmail(credentialsDTO.email())
                .orElseThrow(() -> new ResourceNotFoundException("Unknown user"));
        Profile profile = user.getProfile();
        if (isMatchedPassword(credentialsDTO, user.getPassword())) {
            return AuthenticationResponse.builder()
                    .firstname(getFirstname(profile.getFullname()))
                    .lastname(getLastname(profile.getFullname()))
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
        Profile profile = profileRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
        return AuthenticationResponse.builder()
                .firstname(getFirstname(profile.getFullname()))
                .lastname(getLastname(profile.getFullname()))
                .email(user.getEmail())
                .accessToken(tokenConfiguration.getCurrentToken(httpServletRequest))
                .build();
    }

    public static User authenticated() {
        try {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    private String getFirstname(String fullname) {
        String name = getPiecesOfFullname(fullname)[0];
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    private String getLastname(String fullname) {
        String[] piecesOfTheFullname = getPiecesOfFullname(fullname);
        String name = piecesOfTheFullname[piecesOfTheFullname.length - 1];
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    private String[] getPiecesOfFullname(String fullname) {
        if (isInvalidFullname(fullname)) {
            return new String[0];
        };
        return fullname.split(" ");
    }

    private static boolean isInvalidFullname(String fullname) {
        return fullname == null || fullname.trim().isEmpty();
    }

    private boolean isMatchedPassword(CredentialsDTO credentialsDTO, String encodedPassword) {
        return passwordEncoder.matches(credentialsDTO.password(), encodedPassword);
    }
}
