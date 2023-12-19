package br.com.karatedopi.entities.enums;

import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum que contém todos os status de usuário.
 * @author Jonatas Blendo dos Santos Laet
 */
@Getter
@RequiredArgsConstructor
public enum UserStatus {

	PENDING_EVALUATION("Pending evaluation"),
	ACTIVE("Active"),
	SUSPENDED("Suspended");

	private final String name;

	public String getName() {
		return name;
	}

	public static UserStatus getByValue(String value) {
		for (UserStatus status : values()) {
			if (status.getName().equalsIgnoreCase(value)) {
				return status;
			}
		}
		throw new ResourceNotFoundException("No enum constant with name " + value);
	}

}
