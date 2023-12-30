package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Tournament;
import br.com.karatedopi.entities.enums.TournamentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime eventDateTime;

	@Builder.Default
	private List<TournamentParticipantDTO> participants = new ArrayList<>();

	public static TournamentDTO getTournamentDTO(Tournament tournament) {
		return TournamentDTO.builder()
				.id(tournament.getId())
				.name(tournament.getName())
				.status(tournament.getStatus())
				.address(AddressDTO.getAddressDTO(tournament.getAddress()))
				.eventDateTime(tournament.getEventDate())
				.participants(
					tournament.getParticipants().stream()
						.map(TournamentParticipantDTO::getTournamentParticipantDTO)
						.collect(Collectors.toList())
				)
				.build();
	}
}
