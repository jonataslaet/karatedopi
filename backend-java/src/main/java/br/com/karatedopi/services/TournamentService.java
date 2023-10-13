package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.TournamentDTO;
import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.Tournament;
import br.com.karatedopi.repositories.AddressRepository;
import br.com.karatedopi.repositories.TournamentRepository;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class TournamentService {

	@Autowired
	TournamentRepository tournamentRepository;

	@Autowired
	CityService cityService;

	@Autowired
	AddressRepository addressRepository;

	public Page<TournamentDTO> findAllTournaments(String status, Pageable pagination) {
		if (Objects.isNull(status) || status.isEmpty()){
			return tournamentRepository.findAllTournaments(pagination).map(TournamentDTO::getTournamentDTO);
		}
		return tournamentRepository.findAllTournamentsByStatus(status, pagination).map(TournamentDTO::getTournamentDTO);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public TournamentDTO createTournament(TournamentDTO tournamentDTO) {
		Address address = getAddress(tournamentDTO);
		Address savedAddress = addressRepository.save(address);
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
}

