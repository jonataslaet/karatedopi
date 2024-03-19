package br.com.karatedopi.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TournamentParticipationPK {

    @ManyToOne
    @JoinColumn(name="participant_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name="tournament_id")
    private Tournament tournament;
}