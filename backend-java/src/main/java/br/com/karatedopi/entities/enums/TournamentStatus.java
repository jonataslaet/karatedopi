package br.com.karatedopi.entities.enums;

import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum that contains all the tournament statuses.
 * @author Jonatas Blendo dos Santos Laet
 */
@Getter
@RequiredArgsConstructor
public enum TournamentStatus {

	OPENED("Opened"),
	IN_PROGRESS("In progress"),
	SUSPENDED("Suspended"),
	FINISHED("Finished");

	private final String name;

	public String getName() {
		return name;
	}

	public static TournamentStatus getByValue(String value) {
		for (TournamentStatus status : values()) {
			if (status.getName().equalsIgnoreCase(value)) {
				return status;
			}
		}
		throw new ResourceNotFoundException("Nenhuma constante enum com o nome " + value);
	}

}
