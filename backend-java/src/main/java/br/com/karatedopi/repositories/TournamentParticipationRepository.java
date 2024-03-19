package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.ProfileGraduation;
import br.com.karatedopi.entities.Tournament;
import br.com.karatedopi.entities.TournamentParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TournamentParticipationRepository extends JpaRepository<TournamentParticipation, Long> {

    @Query("""
            SELECT tournamentParticipation FROM TournamentParticipation tournamentParticipation
            WHERE tournamentParticipation.id.tournament = :tournament
            """)
    Set<TournamentParticipation> findTournamentParticipationsByTournament(@Param("tournament") Tournament tournament);

    @Query("""
            SELECT tournamentParticipation FROM TournamentParticipation tournamentParticipation
            WHERE tournamentParticipation.id.tournament.id = :tournamentId AND
            tournamentParticipation.id.profile.id = :profileId
            """)
    TournamentParticipation findTournamentParticipation(
            @Param("tournamentId") Long tournamentId, @Param("profileId") Long profileId);

    @Modifying
    @Query("DELETE FROM TournamentParticipation t WHERE t.id.tournament.id = :tournamentId AND t.id.profile.id = :profileId")
    void deleteByTournamentIdAndProfileId(@Param("tournamentId") Long tournamentId, @Param("profileId") Long profileId);

}
