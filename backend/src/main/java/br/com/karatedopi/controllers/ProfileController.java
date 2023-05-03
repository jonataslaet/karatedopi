package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.ProfileDTO;
import br.com.karatedopi.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;
	
	@GetMapping
	public ResponseEntity<Page<ProfileDTO>> getPagedProfiles(@RequestParam(required = false) String uf, @PageableDefault(page = 0, size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<ProfileDTO> profiles = profileService.getPagedProfiles(uf, pageable);
		return ResponseEntity.ok().body(profiles);
	}

}
