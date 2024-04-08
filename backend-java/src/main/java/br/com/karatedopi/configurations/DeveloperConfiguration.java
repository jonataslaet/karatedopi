package br.com.karatedopi.configurations;

import br.com.karatedopi.services.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Profile("dev")
@Configuration
public class DeveloperConfiguration {

    private final DatabaseService databaseService;

    @Bean
    public boolean instanciaDatabase() {
        return databaseService.instanciaDataBase();
    }
}
