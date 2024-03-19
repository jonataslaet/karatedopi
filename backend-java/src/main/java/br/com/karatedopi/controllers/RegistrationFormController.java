package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.RegistrationFormInputDTO;
import br.com.karatedopi.controllers.dtos.RegistrationFormOutputDTO;
import br.com.karatedopi.services.RegistrationFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/registrationforms")
@RequiredArgsConstructor
public class RegistrationFormController {

    private final RegistrationFormService registrationFormService;

    @PostMapping
    public ResponseEntity<RegistrationFormOutputDTO> createRegistration(
            @RequestBody RegistrationFormInputDTO registerDTO
    ) {
        RegistrationFormOutputDTO createdUser = registrationFormService.createRegistration(registerDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getUserOutputDTO().getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN')")
    public ResponseEntity<Void> deleteRegistration(@PathVariable("id") Long userId){
        registrationFormService.deleteRegistrationByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<RegistrationFormOutputDTO> readRegistrationFormByUserId(@PathVariable("id") Long userId){
        RegistrationFormOutputDTO registrationFormDTO = registrationFormService.readRegistrationFormByUserId(userId);
        return ResponseEntity.ok(registrationFormDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN')")
    public ResponseEntity<Void> updateRegistrationFormByUserId(@PathVariable("id") Long userId, @RequestBody RegistrationFormInputDTO registrationFormDTO){
        registrationFormService.updateRegistrationFormByUserId(userId, registrationFormDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<Void> updateMyRegistrationForm(@RequestBody RegistrationFormInputDTO registrationFormDTO){
        registrationFormService.updateRegistrationForm(registrationFormDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Page<RegistrationFormOutputDTO>> getPagedRegistrationForms(
            @RequestParam(name = "search", required = false) String search,
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<RegistrationFormOutputDTO> registrationFormsDTOs = registrationFormService.getPagedRegistrationForms(search, pageable);
        return ResponseEntity.ok().body(registrationFormsDTOs);
    }
}
