package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.AuthenticationResponse;
import br.com.karatedopi.controllers.dtos.CredentialsDTO;
import br.com.karatedopi.controllers.dtos.SendingEmailDTO;
import br.com.karatedopi.services.AuthService;
import br.com.karatedopi.services.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

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

    @PostMapping("/email")
    public ResponseEntity<Void> sendingEmail(@RequestParam(value = "file", required = false) MultipartFile[] files,
        SendingEmailDTO sendingEmailDTO) {
        emailService.sendEmail(files, sendingEmailDTO);
        return ResponseEntity.noContent().build();
    }

}
