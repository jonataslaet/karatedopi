package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.ProfileCreateDTO;
import br.com.karatedopi.controllers.dtos.ProfileReadDTO;
import br.com.karatedopi.controllers.dtos.ProfileUpdateDTO;
import br.com.karatedopi.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;
	
	@GetMapping
	public ResponseEntity<Page<ProfileReadDTO>> getPagedProfiles(
			@RequestParam(required = false) String state,
			@PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<ProfileReadDTO> profiles = profileService.getPagedProfiles(state, pageable);
		return ResponseEntity.ok().body(profiles);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProfileReadDTO> getProfileReadResponse(@PathVariable("id") Long id){
		ProfileReadDTO profile = profileService.getProfileReadResponseDTO(id);
		return ResponseEntity.ok().body(profile);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProfileUpdateDTO> updateProfile(@PathVariable("id") Long id, @RequestBody ProfileCreateDTO profileCreateDTO){
		ProfileUpdateDTO updatedProfile = profileService.updateProfile(id, profileCreateDTO);
		return ResponseEntity.ok().body(updatedProfile);
	}
}
