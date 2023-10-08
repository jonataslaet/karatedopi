package br.com.karatedopi.entities.enums;

import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum que contém todos os estados brasileiros.
 * @author Jerônimo Nunes Rocha
 * Adaptado por Jonatas Laet em 09/05/2021
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
		throw new ResourceNotFoundException("No enum constant with name " + value);
	}

}
