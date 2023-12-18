package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.AuthenticationResponse;
import br.com.karatedopi.controllers.dtos.CredentialsDTO;
import br.com.karatedopi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<AuthenticationResponse> current() {
        AuthenticationResponse authenticationResponse = authService.current();
        return ResponseEntity.ok(authenticationResponse);
    }

}
