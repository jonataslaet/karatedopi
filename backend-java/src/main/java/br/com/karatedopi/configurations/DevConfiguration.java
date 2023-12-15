package br.com.karatedopi.configurations;

import br.com.karatedopi.services.DBService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DevConfiguration {

    private final DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    String strategy;

    @Bean
    public boolean instanciaDatabase() {

        if (!strategy.contains("create")) {
            return false;
        }

        return dbService.mockDatabase();
    }
}
