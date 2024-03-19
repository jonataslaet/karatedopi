package br.com.karatedopi.services;

import br.com.karatedopi.configurations.TokenConfiguration;
import br.com.karatedopi.controllers.dtos.AuthenticationResponse;
import br.com.karatedopi.controllers.dtos.CredentialsDTO;
import br.com.karatedopi.controllers.dtos.EmailDTO;
import br.com.karatedopi.controllers.dtos.PasswordResetDTO;
import br.com.karatedopi.controllers.dtos.SendingEmailDTO;
import br.com.karatedopi.entities.PasswordRecovery;
import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.repositories.UserRepository;
import br.com.karatedopi.services.exceptions.InvalidAuthenticationException;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String passwordRecoveryUri;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfiguration tokenConfiguration;
    private final HttpServletRequest httpServletRequest;
    private final PasswordRecoveryService passwordRecoveryService;
    private final EmailService emailService;

    @Transactional(readOnly = true)
    public AuthenticationResponse login(CredentialsDTO credentialsDTO) {
        User user = userRepository.findByEmail(credentialsDTO.email())
                .orElseThrow(() -> new ResourceNotFoundException("Email ou senha estão inválidos"));
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
        throw new InvalidAuthenticationException("Email ou senha estão inválidos");
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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
                throw new InvalidAuthenticationException("O usuário precisa de estar autenticado para fazer esta operação");
            }
            return (User) authentication.getPrincipal();
        } catch (Exception e) {
            throw new InvalidAuthenticationException("O usuário precisa de estar autenticado para fazer esta operação");
        }
    }

    private boolean isMatchedPassword(CredentialsDTO credentialsDTO, String encodedPassword) {
        return passwordEncoder.matches(credentialsDTO.password(), encodedPassword);
    }

    @Transactional
    public void resetPassword(String uuidToken, PasswordResetDTO passwordRenovationDTO) {
        validPasswordRenovation(passwordRenovationDTO);
        List<PasswordRecovery> passwordRecoveries =
                passwordRecoveryService.getValidPasswordRecoveries(uuidToken, Instant.now());
        PasswordRecovery validPasswordRecovery = passwordRecoveries.get(0);
        User user = userRepository.findByEmail(validPasswordRecovery.getEmail()).orElseThrow(() ->
                new ResourceNotFoundException("Usuário não encontrado"));
        validPasswordRecovery.setExpiration(Instant.now());
        user.setPassword(passwordEncoder.encode(passwordRenovationDTO.getNewPassword()));
        saveUserAndPasswordRecovery(validPasswordRecovery, user);
    }

    private void validPasswordRenovation(PasswordResetDTO passwordRenovationDTO) {
        if (!passwordRenovationDTO.getNewPassword().equals(passwordRenovationDTO.getNewPasswordConfirmation())) {
            throw new InvalidAuthenticationException("A senha e a confirmação dela devem ser exatamente iguais");
        }
    }

    @Transactional
    private void saveUserAndPasswordRecovery(PasswordRecovery passwordRecovery, User user) {
        userRepository.save(user);
        passwordRecoveryService.savePasswordRecovery(passwordRecovery);
    }

    @Transactional
    public void recoveryToken(EmailDTO emailDTO) {
        User user = userRepository.findByEmail(emailDTO.getAddress())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        if (Objects.nonNull(user)) {
            String uuidToken = UUID.randomUUID().toString();
            PasswordRecovery passwordRecovery = PasswordRecovery.builder()
                    .token(uuidToken)
                    .email(emailDTO.getAddress())
                    .expiration(Instant.now().plusSeconds(60 * tokenMinutes))
                    .build();
            passwordRecoveryService.savePasswordRecovery(passwordRecovery);
            String emailBody = "Clique no seguinte link para resetar sua senha: \n" + passwordRecoveryUri + uuidToken
                    + "\n\n Esse link expirará daqui a 30 minutos. " +
                    "Portanto, se esse email não foi solicitado por você, apenas o ignore.";
            emailService.sendEmail(SendingEmailDTO.builder()
                    .body(emailBody).to(emailDTO.getAddress()).subject("Resetamento de senha")
                    .build());
        }
    }
}
