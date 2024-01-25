package br.com.karatedopi.factories;

import br.com.karatedopi.entities.Role;

public class FactoryRole {

    public static Role roleRoot() {
        return Role.builder().id(1L).authority("ROLE_ROOT").build();
    }
    public static Role roleAdmin() {
        return Role.builder().id(2L).authority("ROLE_ADMIN").build();
    }

    public static Role roleModerator() {
        return Role.builder().id(3L).authority("ROLE_MODERATOR").build();
    }

    public static Role roleUser() {
        return Role.builder().id(4L).authority("ROLE_USER").build();
    }

}
