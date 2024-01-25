package br.com.karatedopi.entities.enums;

import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum that contains all the user roles.
 * @author Jonatas Blendo dos Santos Laet
 */
@Getter
@RequiredArgsConstructor
public enum UserRole {

	ROLE_ROOT(1, "Root"),
	ROLE_ADMIN(2, "Administrator"),
	ROLE_MODERATOR(3, "Moderator"),
	ROLE_USER(4, "User");

	private final Integer id;
	private final String name;

	public static UserRole getByValue(String value) {
		for (UserRole role : values()) {
			if (role.getName().equalsIgnoreCase(value)) {
				return role;
			}
		}
		throw new ResourceNotFoundException("Nenhuma constante enum com o nome " + value);
	}
}
