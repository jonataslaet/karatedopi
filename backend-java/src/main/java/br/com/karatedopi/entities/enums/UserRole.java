package br.com.karatedopi.entities.enums;

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
}
