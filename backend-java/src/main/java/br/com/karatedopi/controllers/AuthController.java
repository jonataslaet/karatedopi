package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.AuthenticationResponse;
import br.com.karatedopi.controllers.dtos.CredentialsDTO;
import br.com.karatedopi.controllers.dtos.EmailDTO;
import br.com.karatedopi.controllers.dtos.PasswordResetDTO;
import br.com.karatedopi.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody CredentialsDTO credentialsDTO) {
        AuthenticationResponse authenticationResponse = authService.login(credentialsDTO);
        return ResponseEntity.ok(authenticationResponse);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<AuthenticationResponse> current() {
        AuthenticationResponse authenticationResponse = authService.current();
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/recovery-token")
    public ResponseEntity<?> recoveryToken(@RequestBody @Valid EmailDTO emailDTO) {
        authService.recoveryToken(emailDTO);
        return ResponseEntity.ok("Caso esse email exista, será enviado a ele um link para resetar a senha");
    }

    @PostMapping("/new-password/{token}")
    public ResponseEntity<?> renewPassword(@PathVariable String token,
                                           @RequestBody @Valid PasswordResetDTO passwordResetDTO) {
        authService.resetPassword(token, passwordResetDTO);
        return ResponseEntity.noContent().build();
    }

}
