package br.com.karatedopi;

import br.com.karatedopi.controllers.dtos.RegisterForm;
import br.com.karatedopi.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class KaratedopiApplication implements CommandLineRunner {

	private final PasswordEncoder passwordEncoder;

	private final RegistrationService registrationService;

	public static void main(String[] args) {
		SpringApplication.run(KaratedopiApplication.class, args);
	}

	@Override
	public void run(String... args) {

		RegisterForm registerForm01 = RegisterForm.builder()
				.fullname("Roberto Hugo Raul Rocha")
				.cpf("72619735300")
				.rg("422434875")
				.birthday(LocalDate.of(2005, Month.MAY, 6))
				.mother("Alana Giovanna Nair")
				.father("Luan Mário Rocha")
				.email("roberto_hugo_rocha@djapan.com.br")
				.password(passwordEncoder.encode("blendo273"))
				.zipCode("64028220")
				.address("Rua Curitiba")
				.number("649")
				.neighbourhood("Santo Antônio")
				.city("Teresina")
				.state("Piauí")
				.phoneNumbers(Set.of("8637668508", "86988536019"))
				.bloodType("O+")
				.build();

		RegisterForm registerForm02 = RegisterForm.builder()
				.fullname("João Roberto Márcio Barros")
				.cpf("00391725386")
				.rg("219316624")
				.birthday(LocalDate.of(2005, Month.MAY, 2))
				.mother("Ana Isabella")
				.father("Antonio Julio Barros")
				.email("joaorobertobarros@wizardsjc.com.br")
				.password(passwordEncoder.encode("blendo273"))
				.zipCode("64060640")
				.address("Rua José Leite Pereira")
				.number("232")
				.neighbourhood("Porto do Centro")
				.city("Teresina")
				.state("Piauí")
				.phoneNumbers(Set.of("8626279234", "86993482204"))
				.bloodType("O+")
				.build();

		RegisterForm registerForm03 = RegisterForm.builder()
				.fullname("Edson Davi Dias")
				.cpf("57858207300")
				.rg("346208063")
				.birthday(LocalDate.of(2005, Month.JANUARY, 23))
				.mother("Emily Kamilly Lorena")
				.father("Miguel Martin Vitor Dias")
				.email("edson.davi.dias@mesquita.not.br")
				.password(passwordEncoder.encode("blendo273"))
				.zipCode("64046450")
				.address("Rua Fenelon Castelo Branco")
				.number("426")
				.neighbourhood("São João")
				.city("Teresina")
				.state("Piauí")
				.phoneNumbers(Set.of("8635468352", "86996329247"))
				.bloodType("A+")
				.build();

		RegisterForm registerForm04 = RegisterForm.builder()
				.fullname("Mário Thomas Martins")
				.cpf("32570538353")
				.rg("110150727")
				.birthday(LocalDate.of(2005, Month.MAY, 1))
				.mother("Hadassa Vanessa")
				.father("Fernando Danilo Paulo Martins")
				.email("mariothomasmartins@lojaprincezinha.com.br")
				.password(passwordEncoder.encode("blendo273"))
				.zipCode("64013595")
				.address("Quadra 142")
				.number("196")
				.neighbourhood("Santa Maria da Codipe")
				.city("Teresina")
				.state("PI")
				.phoneNumbers(Set.of("8626285586","86985287401"))
				.bloodType("O-")
				.build();

		RegisterForm registerForm05 = RegisterForm.builder()
				.fullname("Daniel Davi Danilo dos Santos")
				.cpf("52046461320")
				.rg("488446375")
				.birthday(LocalDate.of(2005, Month.JANUARY, 23))
				.mother("Hadassa Vanessa")
				.father("Fernando Danilo Paulo Martins")
				.email("mariothomasmartins@lojaprincezinha.com.br")
				.password(passwordEncoder.encode("blendo273"))
				.zipCode("64013595")
				.address("Quadra 142")
				.number("196")
				.neighbourhood("Santa Maria da Codipe")
				.city("Teresina")
				.state("PI")
				.phoneNumbers(Set.of("8626285586","86985287401"))
				.bloodType("O-")
				.build();

		RegisterForm registerForm06 = RegisterForm.builder()
				.fullname("Gael Pietro Silva")
				.cpf("43638039323")
				.rg("454583953")
				.birthday(LocalDate.of(2005, Month.JANUARY, 22))
				.mother("Rosa Nicole")
				.father("Felipe Diogo Silva")
				.email("gael.pietro.silva@acmorgado.com.br")
				.password(passwordEncoder.encode("blendo273"))
				.zipCode("64083195")
				.address("Vila Alto do Muro")
				.number("643")
				.neighbourhood("Colorado")
				.city("Teresina")
				.state("PI")
				.phoneNumbers(Set.of("8626343985","86985205103"))
				.bloodType("B-")
				.build();

		RegisterForm registerForm07 = RegisterForm.builder()
				.fullname("Kaique Marcos Carlos Eduardo Campos")
				.cpf("35545525343")
				.rg("334601125")
				.birthday(LocalDate.of(2003, Month.JANUARY, 23))
				.mother("Luana Isis")
				.father("Miguel Davi Calebe Campos")
				.email("kaique-campos85@akadnyx.com.br")
				.password(passwordEncoder.encode("blendo273"))
				.zipCode("64009300")
				.address("Rua Manoel Bandeira")
				.number("500")
				.neighbourhood("Memorare")
				.city("Teresina")
				.state("PI")
				.phoneNumbers(Set.of("8629186862","86983495848"))
				.bloodType("AB-")
				.build();

		RegisterForm registerForm08 = RegisterForm.builder()
				.fullname("Ian Thiago Joaquim Cardoso")
				.cpf("28890759313")
				.rg("181259473")
				.birthday(LocalDate.of(2001, Month.JANUARY, 23))
				.mother("Louise Alessandra Nair")
				.father("Henrique Noah Felipe Cardoso")
				.email("ian.thiago.cardoso@edu.uniso.br")
				.password(passwordEncoder.encode("blendo273"))
				.zipCode("64033514")
				.address("Quadra 02")
				.number("523")
				.neighbourhood("Santo Antônio")
				.city("Teresina")
				.state("PI")
				.phoneNumbers(Set.of("8635340990","86999074960"))
				.bloodType("O-")
				.build();

		RegisterForm registerForm09 = RegisterForm.builder()
				.fullname("Vitor Levi Souza")
				.cpf("54453394322")
				.rg("450789421")
				.birthday(LocalDate.of(2000, Month.JANUARY, 23))
				.mother("Catarina Giovana Eloá")
				.father("Enrico Anthony Souza")
				.email("vitor-souza84@tigra.com.br")
				.password(passwordEncoder.encode("blendo273"))
				.zipCode("64049620")
				.address("Rua Prisco Medeiros")
				.number("208")
				.neighbourhood("Ininga")
				.city("Teresina")
				.state("PI")
				.phoneNumbers(Set.of("8628725341","86995230151"))
				.bloodType("B+")
				.build();

		RegisterForm registerForm10 = RegisterForm.builder()
				.fullname("Manuel Ian Mendes")
				.cpf("19115370321")
				.rg("394176376")
				.birthday(LocalDate.of(2005, Month.JANUARY, 2))
				.mother("Mariana Yasmin Sarah")
				.father("Kauê Roberto Augusto Mendes")
				.email("manuel_mendes@contabilidadevictoria.com.br")
				.password(passwordEncoder.encode("blendo273"))
				.zipCode("64039040")
				.address("Rua Franco do Vale")
				.number("477")
				.neighbourhood("Esplanada")
				.city("Teresina")
				.state("PI")
				.phoneNumbers(Set.of("8626738035","86984831000"))
				.bloodType("AB-")
				.build();

		RegisterForm registerForm11 = RegisterForm.builder()
				.fullname("Luís Diego Viana")
				.cpf("92037162383")
				.rg("109191146")
				.birthday(LocalDate.of(2005, Month.JANUARY, 23))
				.mother("Catarina Daiane")
				.father("Cauã Vicente Matheus Viana")
				.email("luis-viana93@lta-am.com.br")
				.password(passwordEncoder.encode("blendo273"))
				.zipCode("64079139")
				.address("Rua Josefa Evangelista Coelho")
				.number("369")
				.neighbourhood("Novo Horizonte")
				.city("Teresina")
				.state("PI")
				.phoneNumbers(Set.of("8637434354","86997955814"))
				.bloodType("O-")
				.build();

		RegisterForm registerForm12 = RegisterForm.builder()
				.fullname("Anderson Kauê da Cruz")
				.cpf("59273696341")
				.rg("276699373")
				.birthday(LocalDate.of(2005, Month.JANUARY, 23))
				.mother("Mariane Heloise")
				.father("Luís Enrico da Cruz")
				.email("anderson_kaue_dacruz@madhause.com.br")
				.password(passwordEncoder.encode("blendo273"))
				.zipCode("64053340")
				.address("Rua Elisaldo Reinaldo")
				.number("763")
				.neighbourhood("Santa Isabel")
				.city("Teresina")
				.state("PI")
				.phoneNumbers(Set.of("8638919111","86993050033"))
				.bloodType("B-")
				.build();



		registrationService.createRegistration(registerForm01);
		registrationService.createRegistration(registerForm02);
		registrationService.createRegistration(registerForm03);
		registrationService.createRegistration(registerForm04);
		registrationService.createRegistration(registerForm05);
		registrationService.createRegistration(registerForm06);
//		registrationService.createRegistration(registerForm07);
//		registrationService.createRegistration(registerForm08);
//		registrationService.createRegistration(registerForm09);
//		registrationService.createRegistration(registerForm10);
//		registrationService.createRegistration(registerForm11);
//		registrationService.createRegistration(registerForm12);
	}
}
