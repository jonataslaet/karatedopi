package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.AuthenticationResponse;
import br.com.karatedopi.controllers.dtos.LoginForm;
import br.com.karatedopi.controllers.dtos.RegisterForm;
import br.com.karatedopi.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginForm loginForm
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(loginForm));
    }

    @PostMapping("/refresh-token")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }


}