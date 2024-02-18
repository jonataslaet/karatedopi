package br.com.karatedopi.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum that contains all the tournament statuses.
 * @author Jonatas Blendo dos Santos Laet
 */
@Getter
@RequiredArgsConstructor
public enum TournamentStatus {

	OPENED(1, "Opened"),
	IN_PROGRESS(2, "In progress"),
	SUSPENDED(3, "Suspended"),
	FINISHED(4, "Finished");

	private final Integer id;
	private final String name;
}
