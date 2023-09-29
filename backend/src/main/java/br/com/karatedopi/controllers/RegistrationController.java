package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.RegisterDTO;
import br.com.karatedopi.controllers.dtos.RegisterCreateDTO;
import br.com.karatedopi.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

	private final RegistrationService registrationService;

	@PostMapping
	public ResponseEntity<RegisterDTO> createRegistration(
			@RequestBody RegisterCreateDTO registerDTO
	) {
		RegisterDTO createdUser = registrationService.createRegistration(registerDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRegistration(@PathVariable("id") Long userId){
		registrationService.deleteRegistrationByUserId(userId);
		return ResponseEntity.noContent().build();
	}
}
