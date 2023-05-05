package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.UserInputDTO;
import br.com.karatedopi.controllers.dtos.UserReadResponseDTO;
import br.com.karatedopi.controllers.dtos.UserUpdateResponseDTO;
import br.com.karatedopi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@PutMapping("/{id}")
	public ResponseEntity<UserUpdateResponseDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserInputDTO userInputDTO){
		UserUpdateResponseDTO updatedUser = userService.updateUser(id, userInputDTO);
		return ResponseEntity.ok().body(updatedUser);
	}
}
