package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.ProfileInputDTO;
import br.com.karatedopi.controllers.dtos.ProfileReadResponseDTO;
import br.com.karatedopi.controllers.dtos.ProfileUpdateResponseDTO;
import br.com.karatedopi.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;
	
	@GetMapping
	public ResponseEntity<Page<ProfileReadResponseDTO>> getPagedProfiles(@RequestParam(required = false) String uf, @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<ProfileReadResponseDTO> profiles = profileService.getPagedProfiles(uf, pageable);
		return ResponseEntity.ok().body(profiles);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProfileReadResponseDTO> getProfileReadResponse(@PathVariable("id") Long id){
		ProfileReadResponseDTO profile = profileService.getProfileReadResponseDTO(id);
		return ResponseEntity.ok().body(profile);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProfileUpdateResponseDTO> updateProfile(@PathVariable("id") Long id, @RequestBody ProfileInputDTO profileInputDTO){
		ProfileUpdateResponseDTO updatedProfile = profileService.updateProfile(id, profileInputDTO);
		return ResponseEntity.ok().body(updatedProfile);
	}
}
