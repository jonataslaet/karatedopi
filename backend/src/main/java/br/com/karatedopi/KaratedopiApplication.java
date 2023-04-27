package br.com.karatedopi;

import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.enums.Role;
import br.com.karatedopi.repositories.UserRepository;
import br.com.karatedopi.services.AuthenticationService;
import br.com.karatedopi.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class KaratedopiApplication {

//	@Autowired
//	private AuthenticationService authenticationService;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@Autowired
//	private JwtService jwtService;

	public static void main(String[] args) {
		SpringApplication.run(KaratedopiApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		User user = User.builder()
//				.id(null)
//				.email("jonataslaet@gmail.com")
//				.password(passwordEncoder.encode("12345"))
//				.firstname("Jonatas")
//				.lastname("Laet")
//				.role(Role.ADMIN)
//				.build();
//
//		var savedUser = userRepository.save(user);
//		var jwtToken = jwtService.generateToken(user);
////		var refreshToken = jwtService.generateRefreshToken(user);
//		authenticationService.saveUserToken(savedUser, jwtToken);
//	}
}
