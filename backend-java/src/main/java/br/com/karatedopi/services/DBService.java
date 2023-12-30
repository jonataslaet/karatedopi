package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.AddressDTO;
import br.com.karatedopi.controllers.dtos.RegisterDTO;
import br.com.karatedopi.controllers.dtos.TournamentDTO;
import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.enums.StateAbbreviation;
import br.com.karatedopi.entities.enums.TournamentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DBService {
    private final TournamentService tournamentService;
    private final RegistrationService registrationService;
    private final CityService cityService;

    public boolean mockDatabase() {
        List<City> piauiCities = cityService.getAllCitiesByStateNameOrAbbreviation(StateAbbreviation.PI.getName());
        City cityTeresina = piauiCities.stream().filter(city ->
                city.getName().equalsIgnoreCase("Teresina")).findFirst().orElse(null);

        TournamentDTO tournamentDTO01 = TournamentDTO.builder()
                .name("I Torneio de Karatê")
                .address(AddressDTO.getAddressDTO(Address.builder()
                        .city(cityTeresina)
                        .neighbourhood("Monte Castelo")
                        .street("Rua Antônio Cavour de Miranda")
                        .zipCode("64017310")
                        .number("S/N")
                        .build()))
                .status(TournamentStatus.OPENED)
                .eventDateTime(LocalDateTime.of(2024, 3, 5, 8, 0))
                .build();

        RegisterDTO registerDTO01 = RegisterDTO.builder()
                .fullname("Roberto Hugo Raul Rocha")
                .cpf("72619735300")
                .rg("422434875")
                .birthday(LocalDate.of(2005, Month.MAY, 6))
                .mother("Alana Giovanna Nair")
                .father("Luan Mário Rocha")
                .email("roberto_hugo_rocha@djapan.com.br")
                .password("blendo273")
                .zipCode("64028220")
                .street("Rua Curitiba")
                .number("649")
                .neighbourhood("Santo Antônio")
                .city("Teresina")
                .state("Piauí")
                .phoneNumbers(Set.of("8637668508", "86988536019"))
                .bloodType("O+")
                .build();

        RegisterDTO registerDTO02 = RegisterDTO.builder()
                .fullname("João Roberto Márcio Barros")
                .cpf("00391725386")
                .rg("219316624")
                .birthday(LocalDate.of(2005, Month.MAY, 2))
                .mother("Ana Isabella")
                .father("Antonio Julio Barros")
                .email("joaorobertobarros@wizardsjc.com.br")
                .password("blendo273")
                .zipCode("64060640")
                .street("Rua José Leite Pereira")
                .number("232")
                .neighbourhood("Porto do Centro")
                .city("Teresina")
                .state("Piauí")
                .phoneNumbers(Set.of("8626279234", "86993482204"))
                .bloodType("O+")
                .build();

        RegisterDTO registerDTO03 = RegisterDTO.builder()
                .fullname("Edson Davi Dias")
                .cpf("57858207300")
                .rg("346208063")
                .birthday(LocalDate.of(2005, Month.JANUARY, 23))
                .mother("Emily Kamilly Lorena")
                .father("Miguel Martin Vitor Dias")
                .email("edson.davi.dias@mesquita.not.br")
                .password("blendo273")
                .zipCode("64046450")
                .street("Rua Fenelon Castelo Branco")
                .number("426")
                .neighbourhood("São João")
                .city("Teresina")
                .state("Piauí")
                .phoneNumbers(Set.of("8635468352", "86996329247"))
                .bloodType("A+")
                .build();

        RegisterDTO registerDTO04 = RegisterDTO.builder()
                .fullname("Mário Thomas Martins")
                .cpf("32570538353")
                .rg("110150727")
                .birthday(LocalDate.of(2005, Month.MAY, 1))
                .mother("Hadassa Vanessa")
                .father("Fernando Danilo Paulo Martins")
                .email("mariothomasmartins@lojaprincezinha.com.br")
                .password("blendo273")
                .zipCode("64013595")
                .street("Quadra 142")
                .number("196")
                .neighbourhood("Santa Maria da Codipe")
                .city("Teresina")
                .state("PI")
                .phoneNumbers(Set.of("8626285586","86985287401"))
                .bloodType("O-")
                .build();

        registrationService.createRegistration(registerDTO01);
        registrationService.createRegistration(registerDTO02);
        registrationService.createRegistration(registerDTO03);
        registrationService.createRegistration(registerDTO04);
        tournamentService.createTournament(tournamentDTO01);

        return true;
    }
}
