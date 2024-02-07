package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.TournamentDTO;
import br.com.karatedopi.controllers.dtos.TournamentParticipantDTO;
import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.Tournament;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.Profile;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TournamentService {

	private final TournamentRepository tournamentRepository;
	private final CityService cityService;
	private final AddressService addressService;
	private final ProfileService profileService;

	@Transactional(readOnly = true)
	public Page<TournamentDTO> findAllTournaments(String status, Pageable pagination) {
		if (Objects.isNull(status) || status.isEmpty()){
			return tournamentRepository.findAllTournaments(pagination).map(TournamentDTO::getTournamentDTO);
		}
		return tournamentRepository.findAllTournamentsByStatus(status, pagination).map(TournamentDTO::getTournamentDTO);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public TournamentDTO createTournament(TournamentDTO tournamentDTO) {
		Address savedAddress = saveTournamentAddress(tournamentDTO);
		Tournament tournament = Tournament.builder()
				.eventDate(tournamentDTO.getEventDateTime())
				.name(tournamentDTO.getName())
				.status(tournamentDTO.getStatus())
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

	public Address getAddress(TournamentDTO tournamentDTO) {
		City city = getCity(tournamentDTO);
		return Address.builder()
				.number(tournamentDTO.getAddress().getNumber())
				.zipCode(tournamentDTO.getAddress().getZipCode())
				.neighbourhood(tournamentDTO.getAddress().getNeighbourhood())
				.street(tournamentDTO.getAddress().getStreet())
				.city(city)
				.build();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	private City getCity(TournamentDTO tournamentDTO) {
		return cityService.getCityByCityNameAndStateName(tournamentDTO.getAddress().getCity(), tournamentDTO.getAddress().getState());
	}

	@Transactional(propagation = Propagation.SUPPORTS)
    public void deleteTournamentById(Long id) {
		findTournamentById(id);
		tournamentRepository.deleteById(id);
	}

	public TournamentDTO getTournamentById(Long id) {
		Tournament tournament = findTournamentById(id);
		return TournamentDTO.getTournamentDTO(tournament);
	}

	@Transactional(readOnly = true)
	private Tournament findTournamentById(Long id) {
		return tournamentRepository.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Nenhum torneio foi encontrado com o id " + id));
	}

	@Transactional
	public TournamentDTO updateTournament(Long id, TournamentDTO tournamentDTO) {
		Tournament tournament = findTournamentById(id);
		if (isDifferentAddresses(tournament.getAddress(), getAddress(tournamentDTO))) {
			Address savedAddress = saveTournamentAddress(tournamentDTO);
			tournament.setAddress(savedAddress);
		}
		tournament.setName(tournamentDTO.getName());
		tournament.setStatus(tournamentDTO.getStatus());
		tournament.setEventDate(tournamentDTO.getEventDateTime());
		try {
			Tournament savedTournament = tournamentRepository.save(tournament);
			return TournamentDTO.getTournamentDTO(savedTournament);
		} catch (Exception e) {
			throw new ResourceStorageException("Problema desconhecido ao salvar torneio");
		}
	}

	private Address saveTournamentAddress(TournamentDTO tournamentDTO) {
		Address address = getAddress(tournamentDTO);
		return addressService.saveAddress(address);
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
		toggleParticipant(foundTournament, authenticatedProfile);
		try {
			Tournament savedTournament = tournamentRepository.save(foundTournament);
			return TournamentDTO.getTournamentDTO(savedTournament);
		} catch (Exception e) {
			throw new ResourceStorageException("Problema desconhecido ao salvar torneio");
		}
	}

	public void toggleParticipant(Tournament tournament, Profile profile) {
		if (tournament.getParticipants().contains(profile)) {
			removeParticipant(tournament, profile);
		} else {
			addParticipant(tournament, profile);
		}
	}

	public List<TournamentParticipantDTO> findParticipants(Long id) {
		List<TournamentParticipantDTO> participantDTOs = new ArrayList<>();
		Tournament foundTournament = findTournamentById(id);
		foundTournament.getParticipants().forEach(participant ->
				participantDTOs.add(TournamentParticipantDTO.getTournamentParticipantDTO(participant)));
		return participantDTOs;
	}

	private void addParticipant(Tournament tournament, Profile profile) {
		tournament.getParticipants().add(profile);
	}

	private void removeParticipant(Tournament tournament, Profile profile) {
		tournament.getParticipants().remove(profile);
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

