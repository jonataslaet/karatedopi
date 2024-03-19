package br.com.karatedopi.factories;

import br.com.karatedopi.controllers.dtos.UserOutputDTO;
import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.enums.UserRole;
import br.com.karatedopi.entities.enums.UserStatus;

import java.util.*;
import java.util.stream.Collectors;

public class FactoryUser {

    public static User createUser(Long userId, String email, String password, String firstname,
        String lastname, UserStatus status, UserRole userRole) {
        Role roleAdmin = Role.builder().authority(userRole.toString()).build();
        return User.builder()
                .id(userId)
                .email(email)
                .password(password)
                .firstname(firstname)
                .lastname(lastname)
                .status(status)
                .roles(new HashSet<>(Collections.singletonList(roleAdmin)))
                .build();
    }

    public static List<User> createAllUsers() {
        Long id = 1L;
        List<User> users = new ArrayList<>();
        users.add(createUser(id, "blendolove@hotmail.com", "", "Jonatas", "Laet",
                UserStatus.ACTIVE, UserRole.ROLE_USER));
        return users;
    }

    public static List<UserOutputDTO> createAllUserOutputDTOs() {
        return createAllUsers().stream().map(UserOutputDTO::getUserOutputDTO).collect(Collectors.toList());
    }

}