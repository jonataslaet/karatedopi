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

    OPENED(1, "Aberto"),
    IN_PROGRESS(2, "Em andamento"),
    SUSPENDED(3, "Suspenso"),
    FINISHED(4, "Finalizado");

    private final Integer id;
    private final String name;

    public static TournamentStatus getValue(String status) {
        for (TournamentStatus tournamentStatus : values()) {
            if (tournamentStatus.getName().equalsIgnoreCase(status)
                    || tournamentStatus.toString().equalsIgnoreCase(status)) {
                return tournamentStatus;
            }
        }
        throw new ResourceNotFoundException("Nenhuma constante enum com o nome " + status);
    }
}