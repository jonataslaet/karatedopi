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

	OPENED(1, "Aberto"),
	IN_PROGRESS(2, "Em andamento"),
	SUSPENDED(3, "Suspenso"),
	FINISHED(4, "Finalizado");

	private final Integer id;
	private final String name;
}
