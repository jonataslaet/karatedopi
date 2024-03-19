package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.AssociationOutputDTO;
import br.com.karatedopi.controllers.dtos.AssociationInputDTO;
import br.com.karatedopi.services.AssociationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/associations")
@RequiredArgsConstructor
public class AssociationController {

    private final AssociationService associationService;

    @GetMapping("/abbreviations/all")
    public ResponseEntity<List<String>> getAllAssociations(){
        List<String> allAssociationAbbreviations = associationService.getAssociationAbbreviations();
        return ResponseEntity.ok().body(allAssociationAbbreviations);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public ResponseEntity<AssociationOutputDTO> createAssociation(
            @RequestBody AssociationInputDTO associationInputDTO
    ) {
        AssociationOutputDTO createdAssociation = associationService.createAssociation(associationInputDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdAssociation.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdAssociation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAssociation(@PathVariable("id") Long associationId){
        associationService.deleteById(associationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN')")
    public ResponseEntity<AssociationOutputDTO> readAssociation(@PathVariable("id") Long associationId){
        AssociationOutputDTO associationOutputDTO = associationService.readById(associationId);
        return ResponseEntity.ok(associationOutputDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN')")
    public ResponseEntity<Void> updateRegistration(@PathVariable("id") Long associationId, @RequestBody AssociationInputDTO associationInputDTO){
        associationService.updateAssociation(associationId, associationInputDTO);
        return ResponseEntity.noContent().build();
    }

}
