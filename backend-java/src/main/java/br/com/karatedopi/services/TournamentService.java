package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.TournamentDTO;
import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.Tournament;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.Profile;

import br.com.karatedopi.repositories.AddressRepository;
import br.com.karatedopi.repositories.ProfileRepository;
import br.com.karatedopi.repositories.TournamentRepository;
import br.com.karatedopi.services.exceptions.InvalidAuthenticationException;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TournamentService {

	private final TournamentRepository tournamentRepository;
	private final CityService cityService;
	private final AddressRepository addressRepository;
	private final ProfileRepository profileRepository;
	
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
		Tournament savedTournament = tournamentRepository.save(tournament);
		savedTournament.setAddress(savedAddress);
		return TournamentDTO.getTournamentDTO(savedTournament);
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

	private Tournament findTournamentById(Long id) {
		return tournamentRepository.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("No tournament with id " + id + " was found"));
	}

	public TournamentDTO updateTournament(Long id, TournamentDTO tournamentDTO) {
		Tournament tournament = findTournamentById(id);
		if (isDifferentAddresses(tournament.getAddress(), getAddress(tournamentDTO))) {
			Address savedAddress = saveTournamentAddress(tournamentDTO);
			tournament.setAddress(savedAddress);
		}
		tournament.setName(tournamentDTO.getName());
		tournament.setStatus(tournamentDTO.getStatus());
		tournament.setEventDate(tournamentDTO.getEventDateTime());
		Tournament savedTournament = tournamentRepository.save(tournament);
		return TournamentDTO.getTournamentDTO(savedTournament);
	}

	private Address saveTournamentAddress(TournamentDTO tournamentDTO) {
		Address address = getAddress(tournamentDTO);
		return addressRepository.save(address);
	}

	private Boolean isDifferentAddresses(Address address1, Address address2) {
		return !address1.getStreet().equalsIgnoreCase(address2.getStreet()) ||
				!address1.getNumber().equalsIgnoreCase(address2.getNumber()) ||
				!address1.getZipCode().equalsIgnoreCase(address2.getZipCode()) ||
				!address1.getNeighbourhood().equalsIgnoreCase(address2.getNeighbourhood()) ||
				!address1.getCity().getName().equalsIgnoreCase(address2.getCity().getName()) ||
				!address1.getCity().getState().getName().equalsIgnoreCase(address2.getCity().getState().getName());
	}

	public TournamentDTO participateInTournament(Long id) {
		User user = AuthService.authenticated();
		if (Objects.isNull(user)) {
			throw new InvalidAuthenticationException("The user needs to be authenticated for performing this operation");
		}
		Profile profile = profileRepository.findById(user.getId()).orElseThrow(() ->
				new ResourceNotFoundException("No profile found"));
		Tournament foundTournament = findTournamentById(id);
		foundTournament.getParticipants().add(profile);
		Tournament savedTournament = tournamentRepository.save(foundTournament);
		return TournamentDTO.getTournamentDTO(savedTournament);
	}
}

