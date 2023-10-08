package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Tournament;
import br.com.karatedopi.entities.enums.TournamentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentDTO {
	private Long id;
	private String name;
	private TournamentStatus status;
	private AddressDTO address;
	private LocalDateTime eventDateTime;

	public static TournamentDTO getTournamentDTO(Tournament tournament) {
		return TournamentDTO.builder()
				.id(tournament.getId())
				.name(tournament.getName())
				.status(tournament.getStatus())
				.address(AddressDTO.getAddressDTO(tournament.getAddress()))
				.eventDateTime(tournament.getEventDate())
				.build();
	}
}
