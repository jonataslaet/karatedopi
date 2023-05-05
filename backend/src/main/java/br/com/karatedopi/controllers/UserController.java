package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.*;
import br.com.karatedopi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<Page<UserReadResponseDTO>> findAllUsersPaged(@PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<UserReadResponseDTO> users = userService.findAllPaged(pageable);
		return ResponseEntity.ok().body(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserReadResponseDTO> findUser(@PathVariable("id") Long id) {
		UserReadResponseDTO user = userService.findUser(id);
		return ResponseEntity.ok().body(user);
	}

	@PostMapping
	public ResponseEntity<UserCreateResponseDTO> createUserAndProfile(
			@RequestBody RegisterForm registerForm
	) {
		UserCreateResponseDTO createdUser = userService.createUserAndProfile(registerForm);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdUser);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserUpdateResponseDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserInputDTO userInputDTO){
		UserUpdateResponseDTO updatedUser = userService.updateUser(id, userInputDTO);
		return ResponseEntity.ok().body(updatedUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
