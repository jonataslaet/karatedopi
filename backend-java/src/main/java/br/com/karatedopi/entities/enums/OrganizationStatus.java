package br.com.karatedopi.entities.enums;

import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum that contains all the organizations statuses.
 * @author Jonatas Blendo dos Santos Laet
 */
@Getter
@RequiredArgsConstructor
public enum OrganizationStatus {

    INACTIVE(1, "Inativo"),
    ACTIVE(2, "Ativo"),
    SUSPENDED(3, "Suspenso");

    private final Integer id;
    private final String name;

    public static OrganizationStatus getValueByValueOrName(String valueOrName) {
        for (OrganizationStatus status : values()) {
            if (status.toString().equalsIgnoreCase(valueOrName) || status.getName().equalsIgnoreCase(valueOrName)) {
                return status;
            }
        }
        throw new ResourceNotFoundException("Nenhuma constante enum com o nome " + valueOrName);
    }
}
