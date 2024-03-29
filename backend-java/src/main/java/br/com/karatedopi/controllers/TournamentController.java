package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.TournamentDTO;
import br.com.karatedopi.controllers.dtos.TournamentParticipantDTO;
import br.com.karatedopi.services.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<Page<TournamentDTO>> getPagedTournaments(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "status", required = false) String status,
            @PageableDefault(sort="eventDate", direction = Direction.DESC) Pageable pageable
    ){
        Page<TournamentDTO> pagedTournaments = tournamentService.findAllTournaments(search, status, pageable);
        return ResponseEntity.ok().body(pagedTournaments);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN')")
    public ResponseEntity<TournamentDTO> createTournament(
            @RequestBody TournamentDTO registerDTO
    ) {
        TournamentDTO createdTournament = tournamentService.createTournament(registerDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTournament.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdTournament);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<TournamentDTO> getTournament(@PathVariable("id") Long id){
        TournamentDTO gotTournament = tournamentService.getTournamentById(id);
        return ResponseEntity.ok().body(gotTournament);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROOT_ADMIN')")
    public ResponseEntity<TournamentDTO> updateTournament(@PathVariable("id") Long id,
        @RequestBody TournamentDTO tournamentDTO) {
        TournamentDTO updatedTournament = tournamentService.updateTournament(id, tournamentDTO);
        return ResponseEntity.ok().body(updatedTournament);
    }

    @PatchMapping("/{id}/participations")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<TournamentDTO> participateInTournament(@PathVariable("id") Long id){
        TournamentDTO updatedUser = tournamentService.updateParticipationInTournament(id);
        return ResponseEntity.ok().body(updatedUser);
    }

    @GetMapping("/{id}/participations")
    @PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<List<TournamentParticipantDTO>> findParticipants(@PathVariable("id") Long id){
        List<TournamentParticipantDTO> participants = tournamentService.findParticipants(id);
        return ResponseEntity.ok().body(participants);
    }
}