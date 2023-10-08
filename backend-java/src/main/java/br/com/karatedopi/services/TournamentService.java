package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.TournamentDTO;
import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.Tournament;
import br.com.karatedopi.repositories.AddressRepository;
import br.com.karatedopi.repositories.TournamentRepository;
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
	public TournamentDTO save(TournamentDTO tournamentDTO01) {
		Address address = getAddress(tournamentDTO01);
		Address savedAddress = addressRepository.save(address);
		Tournament tournament = Tournament.builder()
				.eventDate(tournamentDTO01.getEventDateTime())
				.name(tournamentDTO01.getName())
				.status(tournamentDTO01.getStatus())
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
}

