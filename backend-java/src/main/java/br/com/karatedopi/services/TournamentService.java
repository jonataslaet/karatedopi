package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.TournamentDTO;
import br.com.karatedopi.controllers.dtos.TournamentParticipantDTO;
import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.Tournament;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.TournamentParticipation;
import br.com.karatedopi.entities.enums.TournamentStatus;
import br.com.karatedopi.entities.enums.UserStatus;
import br.com.karatedopi.repositories.TournamentRepository;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import br.com.karatedopi.services.exceptions.ResourceStorageException;
import br.com.karatedopi.services.exceptions.TournamentParticipationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final AddressService addressService;
    private final ProfileService profileService;
    private final TournamentParticipationService tournamentParticipationService;

    @Transactional(readOnly = true)
    public Page<TournamentDTO> findAllTournaments(String search, Pageable pagination) {
        return tournamentRepository.findAllBySearchContent(search, pagination).map(TournamentDTO::getTournamentDTO);
    }

    @Transactional
    public TournamentDTO createTournament(TournamentDTO tournamentDTO) {
        Address tournamentAddress = addressService.getAddressFromTournamentDTO(tournamentDTO);
        Address savedAddress = addressService.saveAddress(tournamentAddress);
        Tournament tournament = Tournament.builder()
                .eventDate(tournamentDTO.getEventDateTime())
                .name(tournamentDTO.getName())
                .status(TournamentStatus.getValue(tournamentDTO.getStatus().toString()))
                .address(savedAddress)
                .build();
        try {
            Tournament savedTournament = tournamentRepository.save(tournament);
            savedTournament.setAddress(savedAddress);
            return TournamentDTO.getTournamentDTO(savedTournament);
        }
        catch (Exception e) {
            throw new ResourceStorageException("Problema desconhecido ao salvar torneio");
        }
    }

    public TournamentDTO getTournamentById(Long id) {
        Tournament tournament = findTournamentById(id);
        return TournamentDTO.getTournamentDTO(tournament);
    }

    @Transactional
    public TournamentDTO updateTournament(Long id, TournamentDTO tournamentDTO) {
        Tournament tournament = findTournamentById(id);
        Address tournamentAddress = tournament.getAddress();
        Address updatedTournamentAddress = addressService.getAddressFromTournamentDTO(tournamentDTO);
        if (isDifferentAddresses(tournamentAddress, updatedTournamentAddress)) {
            tournamentAddress = addressService.updateOldAddressFromNewAddress(tournamentAddress, updatedTournamentAddress);
            tournament.setAddress(tournamentAddress);
        }
        tournament.setName(tournamentDTO.getName());
        tournament.setStatus(TournamentStatus.getValue(tournamentDTO.getStatus().toString()));
        tournament.setEventDate(tournamentDTO.getEventDateTime());
        try {
            Tournament savedTournament = tournamentRepository.save(tournament);
            return TournamentDTO.getTournamentDTO(savedTournament);
        } catch (Exception e) {
            throw new ResourceStorageException("Problema desconhecido ao salvar torneio");
        }
    }

    @Transactional(readOnly = true)
    private Tournament findTournamentById(Long id) {
        return tournamentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Nenhum torneio foi encontrado com o id " + id));
    }

    private Boolean isDifferentAddresses(Address address1, Address address2) {
        return !address1.getStreet().equalsIgnoreCase(address2.getStreet()) ||
                !address1.getNumber().equalsIgnoreCase(address2.getNumber()) ||
                !address1.getZipCode().equalsIgnoreCase(address2.getZipCode()) ||
                !address1.getNeighbourhood().equalsIgnoreCase(address2.getNeighbourhood()) ||
                !address1.getCity().getName().equalsIgnoreCase(address2.getCity().getName()) ||
                !address1.getCity().getState().getName().equalsIgnoreCase(address2.getCity().getState().getName());
    }

    @Transactional
    public TournamentDTO updateParticipationInTournament(Long id) {
        Profile authenticatedProfile = getAuthenticatedProfile();
        Tournament foundTournament = findTournamentById(id);
        isValidParticipation(foundTournament, AuthService.authenticated());
        try {
            toggleParticipant(foundTournament, authenticatedProfile);
            return TournamentDTO.getTournamentDTO(foundTournament);
        } catch (Exception e) {
            throw new ResourceStorageException("Problema desconhecido ao salvar torneio");
        }
    }

    public void toggleParticipant(Tournament tournament, Profile profile) {
        TournamentParticipation tournamentParticipation =
                tournament.getParticipants().stream().filter(t ->
                        Objects.equals(t.getProfile().getId(), profile.getId())).findFirst().orElse(null);
        if (Objects.nonNull(tournamentParticipation)) {
            tournament.getParticipants().remove(tournamentParticipation);
            profile.getTournaments().remove(tournamentParticipation);
            tournamentParticipationService.deleteParticipation(tournament, profile);
        } else {
            tournamentParticipation = TournamentParticipation.builder().build();
            tournamentParticipation.setTournament(tournament);
            tournamentParticipation.setProfile(profile);
            tournament.getParticipants().add(tournamentParticipation);
            profile.getTournaments().add(tournamentParticipation);
            tournamentParticipationService.saveTournamentParticipation(tournamentParticipation);
        }
    }

    public List<TournamentParticipantDTO> findParticipants(Long id) {
        List<TournamentParticipantDTO> participantDTOs = new ArrayList<>();
        Tournament foundTournament = findTournamentById(id);
        loadTournamentParticipations(foundTournament);
        Set<TournamentParticipation> participants = foundTournament.getParticipants();
        participants.forEach(participant ->
                participantDTOs.add(TournamentParticipantDTO.getTournamentParticipantDTO(participant.getProfile())));
        return participantDTOs;
    }

    private void loadTournamentParticipations(Tournament tournament) {
        Set<TournamentParticipation> profileParticipants = tournamentParticipationService
                .getTournamentParticipationsByTournament(tournament);
        tournament.setParticipants(profileParticipants);
    }

    private Profile getAuthenticatedProfile() {
        User user = AuthService.authenticated();
        return profileService.getProfile(user.getId());
    }

    private void isValidParticipation(Tournament foundTournament, User authenticatedUser) {
        isValidTournamentStatus(foundTournament);
        isValidUserStatus(authenticatedUser);
    }

    private void isValidUserStatus(User user) {
        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new TournamentParticipationException("O usuário logado precisa de estar ativo para participar deste torneio.");
        }
    }

    private void isValidTournamentStatus(Tournament foundTournament) {
        if (foundTournament.getStatus().equals(TournamentStatus.SUSPENDED)) {
            throw new TournamentParticipationException("Este torneio está temporariamente suspenso.");
        }
        if (foundTournament.getStatus().equals(TournamentStatus.FINISHED)) {
            throw new TournamentParticipationException("Este torneio está finalizado.");
        }
        if (!foundTournament.getStatus().equals(TournamentStatus.OPENED)) {
            throw new TournamentParticipationException("Este torneio não está aberto para inscrições.");
        }
    }
}
