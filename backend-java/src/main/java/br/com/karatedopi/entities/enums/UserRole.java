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

	ROLE_ROOT("Root"),
	ROLE_ADMIN("Administrator"),
	ROLE_MODERATOR("Moderator"),
	ROLE_USER("User");

	private final String name;

	public String getName() {
		return name;
	}

	public static UserRole getByValue(String value) {
		for (UserRole status : values()) {
			if (status.getName().equalsIgnoreCase(value)) {
				return status;
			}
		}
		throw new ResourceNotFoundException("No enum constant with name " + value);
	}
}
