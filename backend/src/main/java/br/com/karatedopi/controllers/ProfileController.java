package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.ProfileDTO;
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
	public ResponseEntity<Page<ProfileDTO>> getPagedProfiles(@RequestParam(required = false) String uf, @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<ProfileDTO> profiles = profileService.getPagedProfiles(uf, pageable);
		return ResponseEntity.ok().body(profiles);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProfileDTO> getProfile(@PathVariable("id") Long id){
		ProfileDTO profile = profileService.getProfileDTO(id);
		return ResponseEntity.ok().body(profile);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProfileDTO> updateProfile(@PathVariable("id") Long id, @RequestBody ProfileDTO profileDTO){
		ProfileDTO updatedProfile = profileService.updateProfile(id, profileDTO);
		return ResponseEntity.ok().body(updatedProfile);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ProfileDTO> updateProfile(@PathVariable("id") Long id){
		profileService.deleteProfile(id);
		return ResponseEntity.noContent().build();
	}
}
