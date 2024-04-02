package br.com.karatedopi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class KaratedopiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaratedopiApplication.class, args);
	}

}
