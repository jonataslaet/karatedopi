package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.*;
import br.com.karatedopi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
	public ResponseEntity<Page<UserReadDTO>> getPagedUsers(
			@PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<UserReadDTO> users = userService.getPagedUsers(pageable);
		return ResponseEntity.ok().body(users);
	}

	@PutMapping("/{id}/evaluations")
	@PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
	public ResponseEntity<UserReadDTO> evaluateUser(@PathVariable("id") Long id, @RequestBody UserEvaluationDTO userEvaluationDTO){
		UserReadDTO updatedUser = userService.evaluateUser(id, userEvaluationDTO);
		return ResponseEntity.ok().body(updatedUser);
	}
}
