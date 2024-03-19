package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Tournament;
import br.com.karatedopi.entities.enums.TournamentStatus;
import br.com.karatedopi.services.exceptions.ForbiddenOperationException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TournamentDTO {
    private Long id;
    private String name;
    private TournamentStatus status;

    @JsonProperty("address")
    private AddressDTO addressDTO;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime eventDateTime;

    @Builder.Default
    private Set<TournamentParticipantDTO> participants = new HashSet<>();

    public void setEventDateTime(String eventDateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            this.eventDateTime = LocalDateTime.parse(eventDateTime, formatter);
        } catch (DateTimeException e) {
            throw new ForbiddenOperationException(e.getLocalizedMessage());
        }
    }

    public static TournamentDTO getTournamentDTO(Tournament tournament) {
        return TournamentDTO.builder()
                .id(tournament.getId())
                .name(tournament.getName())
                .status(TournamentStatus.getValue(tournament.getStatus().toString()))
                .addressDTO(AddressDTO.getAddressDTO(tournament.getAddress()))
                .eventDateTime(tournament.getEventDate())
                .participants(
                        tournament.getParticipants().stream()
                                .map(tournamentParticipation -> TournamentParticipantDTO.getTournamentParticipantDTO(tournamentParticipation.getProfile()))
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
