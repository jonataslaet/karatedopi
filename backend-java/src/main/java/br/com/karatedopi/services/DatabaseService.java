package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.RegistrationFormInputDTO;
import br.com.karatedopi.controllers.dtos.TournamentDTO;
import br.com.karatedopi.controllers.dtos.FederationInputDTO;
import br.com.karatedopi.controllers.dtos.AssociationInputDTO;
import br.com.karatedopi.controllers.dtos.CityDTO;
import br.com.karatedopi.controllers.dtos.StateDTO;
import br.com.karatedopi.controllers.dtos.AddressDTO;
import br.com.karatedopi.controllers.dtos.ProfileInputDTO;
import br.com.karatedopi.controllers.dtos.UserInputDTO;
import br.com.karatedopi.entities.enums.StateAbbreviation;
import br.com.karatedopi.entities.enums.TournamentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@RequiredArgsConstructor
@Profile("dev")
@Service
public class DatabaseService {

    private final RegistrationFormService registrationFormService;

    private final TournamentService tournamentService;

    private final FederationService federationService;

    private final AssociationService associationService;

    public boolean instanciaDataBase() {

        RegistrationFormInputDTO registrationFormInputDTO01 = RegistrationFormInputDTO.builder()
                .userInputDTO(
                        UserInputDTO.builder()
                                .email("antoniovasconcelos@gmail.com")
                                .password("vasco2024")
                                .firstname("Antônio")
                                .lastname("Vasconcelos")
                                .build()
                )
                .addressDTO(
                        AddressDTO.builder()
                                .zipCode("12345000")
                                .street("Rua das Flores")
                                .number("123")
                                .neighbourhood("Centro")
                                .city(
                                        CityDTO.builder()
                                                .name("São Paulo")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("São Paulo")
                                                                .stateAbbreviation(StateAbbreviation.SP)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .profileInputDTO(
                        ProfileInputDTO.builder()
                                .fullname("Antônio Alves Vasconcelos")
                                .father("João da Silva")
                                .mother("Maria Silva")
                                .bloodType("O+")
                                .birthday(LocalDate.parse("1980-05-15"))
                                .nid("123456789")
                                .itin("98765432100")
                                .phoneNumbers(Set.of("1122334455", "9988776655"))
                                .build()
                )
                .build();

        RegistrationFormInputDTO registrationFormInputDTO02 = RegistrationFormInputDTO.builder()
                .userInputDTO(
                        UserInputDTO.builder()
                                .email("cironesantos@gmail.com")
                                .password("santos2024")
                                .firstname("Cirone")
                                .lastname("Santos")
                                .build()
                )
                .addressDTO(
                        AddressDTO.builder()
                                .zipCode("54321000")
                                .street("Avenida Principal")
                                .number("456")
                                .neighbourhood("Jardim das Flores")
                                .city(
                                        CityDTO.builder()
                                                .name("Rio de Janeiro")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Rio de Janeiro")
                                                                .stateAbbreviation(StateAbbreviation.RJ)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .profileInputDTO(
                        ProfileInputDTO.builder()
                                .fullname("José Cirone dos Santos")
                                .father("Paulo Carvalho")
                                .mother("Juliana Carvalho")
                                .bloodType("B+")
                                .birthday(LocalDate.parse("1992-09-28"))
                                .nid("987654321")
                                .itin("12345678900")
                                .phoneNumbers(Set.of("2123456789", "999887766"))
                                .build()
                )
                .build();

        RegistrationFormInputDTO registrationFormInputDTO03 = RegistrationFormInputDTO.builder()
                .userInputDTO(
                        UserInputDTO.builder()
                                .email("marcoslee@gmail.com")
                                .password("pereira2024")
                                .firstname("Marcos")
                                .lastname("Pereira")
                                .build()
                )
                .addressDTO(
                        AddressDTO.builder()
                                .zipCode("98765000")
                                .street("Rua das Palmeiras")
                                .number("789")
                                .neighbourhood("Bairro Novo")
                                .city(
                                        CityDTO.builder()
                                                .name("Belo Horizonte")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Minas Gerais")
                                                                .stateAbbreviation(StateAbbreviation.MG)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .profileInputDTO(
                        ProfileInputDTO.builder()
                                .fullname("Marcos Aurélio Mendes Pereira")
                                .father("Antônio Almeida")
                                .mother("Fernanda Almeida")
                                .bloodType("AB-")
                                .birthday(LocalDate.parse("1988-12-03"))
                                .nid("456789123")
                                .itin("98765432101")
                                .phoneNumbers(Set.of("3133334444", "988776655"))
                                .build()
                )
                .build();
        RegistrationFormInputDTO registrationFormInputDTO04 = RegistrationFormInputDTO.builder()
                .userInputDTO(
                        UserInputDTO.builder()
                                .email("derickwenner@gmail.com")
                                .password("almeida123")
                                .firstname("Dérick")
                                .lastname("Almeida")
                                .build()
                )
                .addressDTO(
                        AddressDTO.builder()
                                .zipCode("76543000")
                                .street("Rua dos Eucaliptos")
                                .number("987")
                                .neighbourhood("Vila Nova")
                                .city(
                                        CityDTO.builder()
                                                .name("Salvador")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Bahia")
                                                                .stateAbbreviation(StateAbbreviation.BA)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .profileInputDTO(
                        ProfileInputDTO.builder()
                                .fullname("Luciana Oliveira Santos")
                                .father("Luiz Santos")
                                .mother("Elena Oliveira")
                                .bloodType("A+")
                                .birthday(LocalDate.parse("1976-07-20"))
                                .nid("789123456")
                                .itin("65432198700")
                                .phoneNumbers(Set.of("7132109876", "999998888"))
                                .build()
                )
                .build();

        RegistrationFormInputDTO registrationFormInputDTO05 = RegistrationFormInputDTO.builder()
                .userInputDTO(
                        UserInputDTO.builder()
                                .email("felipeduarte@example.com")
                                .password("abc123")
                                .firstname("Felipe")
                                .lastname("Duarte")
                                .build()
                )
                .addressDTO(
                        AddressDTO.builder()
                                .zipCode("13579000")
                                .street("Rua da Praia")
                                .number("246")
                                .neighbourhood("Beira Mar")
                                .city(
                                        CityDTO.builder()
                                                .name("Fortaleza")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Ceará")
                                                                .stateAbbreviation(StateAbbreviation.CE)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .profileInputDTO(
                        ProfileInputDTO.builder()
                                .fullname("Felipe Duarte Lima")
                                .father("Márcio Duarte")
                                .mother("Camila Lima")
                                .bloodType("O-")
                                .birthday(LocalDate.parse("1995-04-10"))
                                .nid("147258369")
                                .itin("98765432102")
                                .phoneNumbers(Set.of("8585858585", "9977778888"))
                                .build()
                )
                .build();

        RegistrationFormInputDTO registrationFormInputDTO06 = RegistrationFormInputDTO.builder()
                .userInputDTO(
                        UserInputDTO.builder()
                                .email("isabelfernandes@example.com")
                                .password("abc123")
                                .firstname("Isabel")
                                .lastname("Fernandes")
                                .build()
                )
                .addressDTO(
                        AddressDTO.builder()
                                .zipCode("24680100")
                                .street("Avenida das Árvores")
                                .number("135")
                                .neighbourhood("Bosque Verde")
                                .city(
                                        CityDTO.builder()
                                                .name("Curitiba")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Paraná")
                                                                .stateAbbreviation(StateAbbreviation.PR)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .profileInputDTO(
                        ProfileInputDTO.builder()
                                .fullname("Isabel Fernandes Santos")
                                .father("Raul Fernandes")
                                .mother("Carla Santos")
                                .bloodType("A-")
                                .birthday(LocalDate.parse("1982-11-25"))
                                .nid("369258147")
                                .itin("123456789")
                                .phoneNumbers(Set.of("4040404040", "988877766"))
                                .build()
                )
                .build();

        RegistrationFormInputDTO registrationFormInputDTO07 = RegistrationFormInputDTO.builder()
                .userInputDTO(
                        UserInputDTO.builder()
                                .email("carloslima@example.com")
                                .password("abc123")
                                .firstname("Carlos")
                                .lastname("Lima")
                                .build()
                )
                .addressDTO(
                        AddressDTO.builder()
                                .zipCode("56789000")
                                .street("Rua das Mangueiras")
                                .number("321")
                                .neighbourhood("Centro")
                                .city(
                                        CityDTO.builder()
                                                .name("Recife")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Pernambuco")
                                                                .stateAbbreviation(StateAbbreviation.PE)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .profileInputDTO(
                        ProfileInputDTO.builder()
                                .fullname("Carlos Lima Santos")
                                .father("José Lima")
                                .mother("Fernanda Santos")
                                .bloodType("B-")
                                .birthday(LocalDate.parse("1985-08-12"))
                                .nid("246813579")
                                .itin("98765432103")
                                .phoneNumbers(Set.of("8131314151", "9777776666"))
                                .build()
                )
                .build();

        RegistrationFormInputDTO registrationFormInputDTO08 = RegistrationFormInputDTO.builder()
                .userInputDTO(
                        UserInputDTO.builder()
                                .email("patriciamoreira@example.com")
                                .password("abc123")
                                .firstname("Patrícia")
                                .lastname("Moreira")
                                .build()
                )
                .addressDTO(
                        AddressDTO.builder()
                                .zipCode("11223344")
                                .street("Avenida Central")
                                .number("789")
                                .neighbourhood("Centro")
                                .city(
                                        CityDTO.builder()
                                                .name("Brasília")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Distrito Federal")
                                                                .stateAbbreviation(StateAbbreviation.DF)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .profileInputDTO(
                        ProfileInputDTO.builder()
                                .fullname("Patrícia Moreira Silva")
                                .father("José Moreira")
                                .mother("Sandra Silva")
                                .bloodType("AB+")
                                .birthday(LocalDate.parse("1983-06-20"))
                                .nid("135792468")
                                .itin("98765432104")
                                .phoneNumbers(Set.of("6131314151", "9444443333"))
                                .build()
                )
                .build();

        RegistrationFormInputDTO registrationFormInputDTO09 = RegistrationFormInputDTO.builder()
                .userInputDTO(
                        UserInputDTO.builder()
                                .email("gustavooliveira@example.com")
                                .password("abc123")
                                .firstname("Gustavo")
                                .lastname("Oliveira")
                                .build()
                )
                .addressDTO(
                        AddressDTO.builder()
                                .zipCode("99887766")
                                .street("Rua da Liberdade")
                                .number("456")
                                .neighbourhood("Liberdade")
                                .city(
                                        CityDTO.builder()
                                                .name("São Luís")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Maranhão")
                                                                .stateAbbreviation(StateAbbreviation.MA)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .profileInputDTO(
                        ProfileInputDTO.builder()
                                .fullname("Gustavo Oliveira Santos")
                                .father("Ricardo Oliveira")
                                .mother("Mariana Santos")
                                .bloodType("O-")
                                .birthday(LocalDate.parse("1990-12-07"))
                                .nid("753159246")
                                .itin("98765432105")
                                .phoneNumbers(Set.of("983456789", "977888999"))
                                .build()
                )
                .build();

        RegistrationFormInputDTO registrationFormInputDTO10 = RegistrationFormInputDTO.builder()
                .userInputDTO(
                        UserInputDTO.builder()
                                .email("carolinecosta@example.com")
                                .password("abc123")
                                .firstname("Caroline")
                                .lastname("Costa")
                                .build()
                )
                .addressDTO(
                        AddressDTO.builder()
                                .zipCode("77553311")
                                .street("Rua dos Coqueiros")
                                .number("987")
                                .neighbourhood("Coqueiral")
                                .city(
                                        CityDTO.builder()
                                                .name("Natal")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Rio Grande do Norte")
                                                                .stateAbbreviation(StateAbbreviation.RN)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .profileInputDTO(
                        ProfileInputDTO.builder()
                                .fullname("Caroline Costa Oliveira")
                                .father("Fernando Costa")
                                .mother("Aline Oliveira")
                                .bloodType("A+")
                                .birthday(LocalDate.parse("1987-04-30"))
                                .nid("951357456")
                                .itin("98765432106")
                                .phoneNumbers(Set.of("8444333222", "988776655"))
                                .build()
                )
                .build();

        TournamentDTO tournamentDTO01 = TournamentDTO.builder()
                .name("Torneio das Estrelas Celestiais")
                .status(TournamentStatus.OPENED)
                .addressDTO(
                        AddressDTO.builder()
                                .street("Avenida das Constelações")
                                .number("42")
                                .zipCode("12345678")
                                .neighbourhood("Cosmos")
                                .city(
                                        CityDTO.builder()
                                                .name("Teresina")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Piauí")
                                                                .stateAbbreviation(StateAbbreviation.PI)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .eventDateTime(LocalDateTime.parse("2030-05-15T10:30:00"))
                .build();

        TournamentDTO tournamentDTO02 = TournamentDTO.builder()
                .name("Grande Torneio do Dragão Vermelho")
                .status(TournamentStatus.OPENED)
                .addressDTO(
                        AddressDTO.builder()
                                .street("Rua do Fogo")
                                .number("7")
                                .zipCode("98765432")
                                .neighbourhood("Montanha Ardente")
                                .city(
                                        CityDTO.builder()
                                                .name("Parnaíba")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Piauí")
                                                                .stateAbbreviation(StateAbbreviation.PI)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .eventDateTime(LocalDateTime.parse("2029-11-20T08:00:00"))
                .build();

        TournamentDTO tournamentDTO03 = TournamentDTO.builder()
                .name("Torneio das Flores de Lótus")
                .status(TournamentStatus.OPENED)
                .addressDTO(
                        AddressDTO.builder()
                                .street("Caminho da Serenidade")
                                .number("108")
                                .zipCode("54321098")
                                .neighbourhood("Jardim Oriental")
                                .city(
                                        CityDTO.builder()
                                                .name("Picos")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Piauí")
                                                                .stateAbbreviation(StateAbbreviation.PI)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .eventDateTime(LocalDateTime.parse("2030-04-12T13:45:00"))
                .build();

        TournamentDTO tournamentDTO04 = TournamentDTO.builder()
                .name("Desafio dos Samurais")
                .status(TournamentStatus.OPENED)
                .addressDTO(
                        AddressDTO.builder()
                                .street("Rua dos Guerreiros")
                                .number("77")
                                .zipCode("13579024")
                                .neighbourhood("Castelo Oriental")
                                .city(
                                        CityDTO.builder()
                                                .name("Campo Maior")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Piauí")
                                                                .stateAbbreviation(StateAbbreviation.PI)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .eventDateTime(LocalDateTime.parse("2029-09-08T11:15:00"))
                .build();

        TournamentDTO tournamentDTO05 = TournamentDTO.builder()
                .name("Torneio do Tigre Branco")
                .status(TournamentStatus.OPENED)
                .addressDTO(
                        AddressDTO.builder()
                                .street("Avenida da Floresta")
                                .number("15")
                                .zipCode("24680135")
                                .neighbourhood("Bosque Selvagem")
                                .city(
                                        CityDTO.builder()
                                                .name("Floriano")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Piauí")
                                                                .stateAbbreviation(StateAbbreviation.PI)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .eventDateTime(LocalDateTime.parse("2030-02-28T09:30:00"))
                .build();

        TournamentDTO tournamentDTO06 = TournamentDTO.builder()
                .name("Desafio do Dragão Dourado")
                .status(TournamentStatus.OPENED)
                .addressDTO(
                        AddressDTO.builder()
                                .street("Rua do Ouro")
                                .number("888")
                                .zipCode("36985214")
                                .neighbourhood("Reino do Sol")
                                .city(
                                        CityDTO.builder()
                                                .name("Piripiri")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Piauí")
                                                                .stateAbbreviation(StateAbbreviation.PI)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .eventDateTime(LocalDateTime.parse("2029-07-17T14:00:00"))
                .build();

        TournamentDTO tournamentDTO07 = TournamentDTO.builder()
                .name("Torneio da Garra de Falcão")
                .status(TournamentStatus.OPENED)
                .addressDTO(
                        AddressDTO.builder()
                                .street("Alameda das Asas")
                                .number("25")
                                .zipCode("58203947")
                                .neighbourhood("Planalto Alado")
                                .city(
                                        CityDTO.builder()
                                                .name("Teresina")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Piauí")
                                                                .stateAbbreviation(StateAbbreviation.PI)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .eventDateTime(LocalDateTime.parse("2030-10-05T12:00:00"))
                .build();

        TournamentDTO tournamentDTO08 = TournamentDTO.builder()
                .name("Torneio da Lua Crescente")
                .status(TournamentStatus.OPENED)
                .addressDTO(
                        AddressDTO.builder()
                                .street("Rua da Noite")
                                .number("123")
                                .zipCode("98765432")
                                .neighbourhood("Céu Noturno")
                                .city(
                                        CityDTO.builder()
                                                .name("Picos")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Piauí")
                                                                .stateAbbreviation(StateAbbreviation.PI)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .eventDateTime(LocalDateTime.parse("2030-08-21T18:30:00"))
                .build();

        TournamentDTO tournamentDTO09 = TournamentDTO.builder()
                .name("Desafio das Águias Douradas")
                .status(TournamentStatus.OPENED)
                .addressDTO(
                        AddressDTO.builder()
                                .street("Avenida dos Altos Voos")
                                .number("555")
                                .zipCode("77777777")
                                .neighbourhood("Cume Celestial")
                                .city(
                                        CityDTO.builder()
                                                .name("Teresina")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Piauí")
                                                                .stateAbbreviation(StateAbbreviation.PI)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .eventDateTime(LocalDateTime.parse("2029-12-10T15:45:00"))
                .build();

        TournamentDTO tournamentDTO10 = TournamentDTO.builder()
                .name("Torneio da Harmonia Karateca")
                .status(TournamentStatus.OPENED)
                .addressDTO(
                        AddressDTO.builder()
                                .street("Rua da União")
                                .number("99")
                                .zipCode("12345678")
                                .neighbourhood("Centro")
                                .city(
                                        CityDTO.builder()
                                                .name("Teresina")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Piauí")
                                                                .stateAbbreviation(StateAbbreviation.PI)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .eventDateTime(LocalDateTime.parse("2030-03-25T09:00:00"))
                .build();

        FederationInputDTO federationDTO = FederationInputDTO.builder()
                .businessName("Federação Piauiense de Karatê")
                .tradeName("FPKT")
                .federationAbbreviation("FPKT")
                .foundationDate(LocalDate.parse("2017-04-04"))
                .ein("27510875000190")
                .email("educadorfisico_09@hotmail.com")
                .phoneNumbers(Set.of("86998001146"))
                .addressDTO(
                        AddressDTO.builder()
                                .zipCode("64078154")
                                .street("Quadra 207 (Cj Dirceu Arcoverde II)")
                                .number("9")
                                .neighbourhood("Itararé")
                                .city(
                                        CityDTO.builder()
                                                .name("Teresina")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Piauí")
                                                                .stateAbbreviation(StateAbbreviation.PI)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        AssociationInputDTO associationDTO1 = AssociationInputDTO.builder()
                .businessName("Associação do Desenvolvimento da Arte do Karatê - do Tradicional")
                .tradeName("ASDAKT")
                .associationAbbreviation("ASDAKT")
                .federationAbbreviation("FPKT")
                .foundationDate(LocalDate.parse("2011-01-28"))
                .ein("13239635000198")
                .phoneNumbers(Set.of("86988034865"))
                .email("")
                .addressDTO(
                        AddressDTO.builder()
                                .zipCode("64016230")
                                .street("Rua Esperanto")
                                .number("541")
                                .neighbourhood("Monte Castelo")
                                .city(
                                        CityDTO.builder()
                                                .name("Teresina")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Piauí")
                                                                .stateAbbreviation(StateAbbreviation.PI)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        AssociationInputDTO associationDTO2 = AssociationInputDTO.builder()
                .businessName("Associação de Karatê de Teresina")
                .tradeName("ASKATE")
                .associationAbbreviation("ASKATE")
                .federationAbbreviation("FPKT")
                .foundationDate(LocalDate.parse("2019-07-15"))
                .ein("41557952000198")
                .phoneNumbers(Set.of("86988034865"))
                .email("")
                .addressDTO(
                        AddressDTO.builder()
                                .zipCode("64066570")
                                .street("Rua Lindalva Barbosa de Neiva")
                                .number("28")
                                .neighbourhood("Cidade Jardim")
                                .city(
                                        CityDTO.builder()
                                                .name("Teresina")
                                                .state(
                                                        StateDTO.builder()
                                                                .name("Piauí")
                                                                .stateAbbreviation(StateAbbreviation.PI)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        registrationFormService.createRegistration(registrationFormInputDTO01);
        registrationFormService.createRegistration(registrationFormInputDTO02);
        registrationFormService.createRegistration(registrationFormInputDTO03);
        registrationFormService.createRegistration(registrationFormInputDTO04);
        registrationFormService.createRegistration(registrationFormInputDTO05);
        registrationFormService.createRegistration(registrationFormInputDTO06);
        registrationFormService.createRegistration(registrationFormInputDTO07);
        registrationFormService.createRegistration(registrationFormInputDTO08);
        registrationFormService.createRegistration(registrationFormInputDTO09);
        registrationFormService.createRegistration(registrationFormInputDTO10);

        tournamentService.createTournament(tournamentDTO01);
        tournamentService.createTournament(tournamentDTO02);
        tournamentService.createTournament(tournamentDTO03);
        tournamentService.createTournament(tournamentDTO04);
        tournamentService.createTournament(tournamentDTO05);
        tournamentService.createTournament(tournamentDTO06);
        tournamentService.createTournament(tournamentDTO07);
        tournamentService.createTournament(tournamentDTO08);
        tournamentService.createTournament(tournamentDTO09);
        tournamentService.createTournament(tournamentDTO10);

        federationService.createFederation(federationDTO);

        associationService.createAssociation(associationDTO1);
        associationService.createAssociation(associationDTO2);

        return true;
    }

}
