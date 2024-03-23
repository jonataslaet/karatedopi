package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.AssociationOutputDTO;
import br.com.karatedopi.controllers.dtos.FederationInputDTO;
import br.com.karatedopi.controllers.dtos.FederationOutputDTO;
import br.com.karatedopi.services.AssociationService;
import br.com.karatedopi.services.FederationService;
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
import java.util.List;

@RestController
@RequestMapping("/federations")
@RequiredArgsConstructor
public class FederationController {

    private final FederationService federationService;
    private final AssociationService associationService;

    @GetMapping("/abbreviations/all")
    public ResponseEntity<List<String>> getAllFederationsAbbreviations(){
        List<String> allFederationsAbbreviations = federationService.getFederationsAbbreviations();
        return ResponseEntity.ok().body(allFederationsAbbreviations);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public ResponseEntity<FederationOutputDTO> createFederation(
            @RequestBody FederationInputDTO federationInputDTO
    ) {
        FederationOutputDTO createdFederation = federationService.createFederation(federationInputDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdFederation.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdFederation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN')")
    public ResponseEntity<Void> deleteFederation(@PathVariable("id") Long federationId){
        federationService.deleteById(federationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN')")
    public ResponseEntity<FederationOutputDTO> readFederation(@PathVariable("id") Long federationId){
        FederationOutputDTO federationOutputDTO = federationService.readById(federationId);
        return ResponseEntity.ok(federationOutputDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN')")
    public ResponseEntity<Void> updateRegistration(@PathVariable("id") Long federationId, @RequestBody FederationInputDTO federationInputDTO){
        federationService.updateFederation(federationId, federationInputDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Page<FederationOutputDTO>> getPagedFederations(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "status", required = false) String status,
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<FederationOutputDTO> federationOutputDTOs = federationService.getPagedFederations(search, status, pageable);
        return ResponseEntity.ok().body(federationOutputDTOs);
    }

    @GetMapping("/{federationId}/associations")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Page<AssociationOutputDTO>> getPagedAssociations(
            @PathVariable("federationId") Long federationId,
            @RequestParam(name = "search", required = false) String search,
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<AssociationOutputDTO> associationOutputDTOs = associationService.getPagedAssociations(federationId, pageable);
        return ResponseEntity.ok().body(associationOutputDTOs);
    }
}
