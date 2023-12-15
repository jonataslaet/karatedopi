package br.com.karatedopi;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class KaratedopiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaratedopiApplication.class, args);
	}

}
