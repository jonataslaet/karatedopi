package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.TournamentCreateDTO;
import br.com.karatedopi.controllers.dtos.TournamentDTO;
import br.com.karatedopi.services.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value="/tournaments")
@RequiredArgsConstructor
public class TournamentController {

	private final TournamentService tournamentService;
	
	@GetMapping
	public ResponseEntity<Page<TournamentDTO>> getPagedTournaments(
		@RequestParam(required = false) String status,
		@PageableDefault(sort="eventDate", direction = Direction.DESC) Pageable pageable
	){
		Page<TournamentDTO> pagedTournaments = tournamentService.findAllTournaments(status, pageable);
		return ResponseEntity.ok().body(pagedTournaments);
	}

	@PostMapping
	public ResponseEntity<TournamentDTO> createTournament(
			@RequestBody TournamentCreateDTO registerDTO
	) {
		TournamentDTO createdTournament = tournamentService.createTournament(registerDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTournament.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdTournament);
	}
}