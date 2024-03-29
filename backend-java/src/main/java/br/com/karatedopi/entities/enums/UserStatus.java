package br.com.karatedopi.entities.enums;

import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum that contains all the user statuses.
 * @author Jonatas Blendo dos Santos Laet
 */
@Getter
@RequiredArgsConstructor
public enum UserStatus {

	PENDING_EVALUATION(1, "Aguardando avaliação"),
	ACTIVE(2, "Ativo"),
	SUSPENDED(3, "Suspenso");

	private final Integer id;
	private final String name;

	public static UserStatus getValueByName(String name) {
		for (UserStatus status : values()) {
			if (status.getName().equalsIgnoreCase(name)) {
				return status;
			}
		}
		throw new ResourceNotFoundException("Nenhuma constante enum com o nome " + name);
	}

	public static UserStatus getValueByValue(String value) {
		for (UserStatus status : values()) {
			if (status.toString().equalsIgnoreCase(value)) {
				return status;
			}
		}
		throw new ResourceNotFoundException("Nenhuma constante enum com o nome " + value);
	}
}
