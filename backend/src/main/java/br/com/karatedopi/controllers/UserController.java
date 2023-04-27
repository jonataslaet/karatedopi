package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.RegisterForm;
import br.com.karatedopi.controllers.dtos.UserDTO;
import br.com.karatedopi.controllers.dtos.UserRegistrationResponse;
import br.com.karatedopi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<Page<UserDTO>> findAllPaged(@PageableDefault(page = 0, size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<UserDTO> usersDTOs = userService.findAllPaged(pageable);
		return ResponseEntity.ok().body(usersDTOs);
	}

	@PostMapping
	public ResponseEntity<UserRegistrationResponse> register(
			@RequestBody RegisterForm registerForm
	) {
		UserRegistrationResponse createdUser = userService.register(registerForm);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdUser);
	}
}
