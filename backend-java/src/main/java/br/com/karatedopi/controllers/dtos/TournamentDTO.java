package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Tournament;
import br.com.karatedopi.entities.enums.TournamentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime eventDateTime;

	@Builder.Default
	private Set<TournamentParticipantDTO> participants = new HashSet<>();

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
						.collect(Collectors.toSet())
				)
				.build();
	}
}
