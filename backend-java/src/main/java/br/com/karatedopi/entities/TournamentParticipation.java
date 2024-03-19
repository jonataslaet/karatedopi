package br.com.karatedopi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_tournament_participant")
public class TournamentParticipation {

    @EmbeddedId
    @Builder.Default
    @EqualsAndHashCode.Include
    private TournamentParticipationPK id = new TournamentParticipationPK();

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    public void setProfile(Profile profile) {
        this.id.setProfile(profile);
    }

    public void setTournament(Tournament tournament) {
        this.id.setTournament(tournament);
    }

    public Profile getProfile() {
        return this.id.getProfile();
    }

    public Tournament getTournament() {
        return this.id.getTournament();
    }

    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedOn = LocalDateTime.now();
    }
}