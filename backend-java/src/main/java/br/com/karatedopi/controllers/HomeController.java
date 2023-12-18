package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.AuthenticationResponse;
import br.com.karatedopi.controllers.dtos.HomeDTO;
import br.com.karatedopi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final AuthService authService;

    @GetMapping
	public ResponseEntity<HomeDTO> getHome() {
        AuthenticationResponse authenticationResponse = authService.current();
        HomeDTO homeDTO = HomeDTO.builder()
                .firstname(authenticationResponse.getFirstname())
                .lastname(authenticationResponse.getLastname())
                .build();
        return ResponseEntity.ok(homeDTO);
    }
}
