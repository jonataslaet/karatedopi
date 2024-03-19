package br.com.karatedopi.configurations;

import br.com.karatedopi.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final TokenConfiguration tokenConfig;
    private final CorsConfigurationSource corsConfigurationSource;

    private final String[] PUBLIC_ENDPOINTS = {"/hello"};
    private final String[] POST_PUBLIC_ENDPOINTS = {"/login", "/registrationforms", "/registrationforms/**", "/recovery-token", "/new-password/**"};
    private final String[] GET_PUBLIC_ENDPOINTS = {
            "/cities**", "/states/all", "/cities/all", "/actuator/health",
            "/associations/abbreviations/all", "/federations/abbreviations/all"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(new JwtAuthFilter(tokenConfig), BasicAuthenticationFilter.class);
        http.sessionManagement(customize -> customize.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(
            authorize -> authorize
                    .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                    .requestMatchers(HttpMethod.POST, POST_PUBLIC_ENDPOINTS).permitAll()
                    .requestMatchers(HttpMethod.GET, GET_PUBLIC_ENDPOINTS).permitAll()
                    .anyRequest().authenticated()
        );
        http.cors(cors -> cors.configurationSource(corsConfigurationSource));

        return http.build();
    }
}