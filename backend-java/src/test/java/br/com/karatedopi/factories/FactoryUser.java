package br.com.karatedopi.factories;

import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FactoryUser {

    public static User newSimpleValidUser() {
        return User.builder()
                .status(UserStatus.PENDING_EVALUATION)
                .roles(Stream.of(Role.builder().id(1L).authority("ROLE_USER").build())
                        .collect(Collectors.toCollection(HashSet::new)))
                .email("teste@gmail.com")
                .firstname("Teste")
                .lastname("Laet")
                .createdOn(LocalDateTime.now())
                .password("teste123")
                .build();
    }
}
