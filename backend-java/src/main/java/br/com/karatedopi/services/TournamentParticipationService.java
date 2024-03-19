package br.com.karatedopi.services;

import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.Tournament;
import br.com.karatedopi.entities.TournamentParticipation;
import br.com.karatedopi.repositories.TournamentParticipationRepository;
import br.com.karatedopi.services.exceptions.ResourceStorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TournamentParticipationService {

    private final TournamentParticipationRepository tournamentParticipationRepository;

    @Transactional(readOnly = true)
    public Set<TournamentParticipation> getTournamentParticipationsByTournament(Tournament tournament) {
        return tournamentParticipationRepository.findTournamentParticipationsByTournament(tournament);
    }

    @Transactional
    public void saveTournamentParticipation(TournamentParticipation tournamentParticipation) {
        try {
            tournamentParticipationRepository.save(tournamentParticipation);
        } catch (Exception e) {
            throw new ResourceStorageException("Problema desconhecido ao salvar participação");
        }
    }

    @Transactional
    public void deleteParticipation(Tournament tournament, Profile profile) {
        tournamentParticipationRepository.deleteByTournamentIdAndProfileId(tournament.getId(), profile.getId());
    }
}
