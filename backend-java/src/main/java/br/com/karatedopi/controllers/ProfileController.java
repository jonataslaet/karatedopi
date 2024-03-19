package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.ProfileOutputDTO;
import br.com.karatedopi.controllers.dtos.GraduationDTO;
import br.com.karatedopi.services.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PatchMapping("/{id}/graduations")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN')")
    public ResponseEntity<ProfileOutputDTO> changeGraduation(@PathVariable("id") Long id,
            @RequestBody @Valid GraduationDTO graduationDTO){
        ProfileOutputDTO graduatedProfile = profileService.changeGraduation(id, graduationDTO);
        return ResponseEntity.ok().body(graduatedProfile);
    }
}

