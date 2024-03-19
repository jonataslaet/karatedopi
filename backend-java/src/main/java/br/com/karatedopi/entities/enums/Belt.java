package br.com.karatedopi.entities.enums;

import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum that contains all karate belts.
 * @author Jonatas Blendo dos Santos Laet
 */
@Getter
@RequiredArgsConstructor
public enum Belt {

    WHITE(1, "Faixa Branca"),
    YELLOW(2, "Faixa Amarela"),
    RED(3, "Faixa Vermelha"),
    ORANGE(4, "Faixa Laranja"),
    GREEN(5, "Faixa Verde"),
    PURPLE(6, "Faixa Roxa"),
    BROWN(7, "Faixa Marrom"),
    BLACK_01(8, "Faixa Preta - 1º Dan"),
    BLACK_02(9, "Faixa Preta - 2º Dan"),
    BLACK_03(10, "Faixa Preta - 3º Dan"),
    BLACK_04(11, "Faixa Preta - 4º Dan"),
    BLACK_05(12, "Faixa Preta - 5º Dan"),
    BLACK_06(13, "Faixa Preta - 6º Dan"),
    BLACK_07(14, "Faixa Preta - 7º Dan"),
    BLACK_08(15, "Faixa Preta - 8º Dan"),
    BLACK_09(16, "Faixa Preta - 9º Dan"),
    BLACK_10(17, "Faixa Preta - 10º Dan");

    private final Integer id;
    private final String description;

    public static Belt getValueByDescriptionOrValue(String name) {
        for (Belt belt : values()) {
            if (belt.getDescription().equalsIgnoreCase(name) || belt.toString().equalsIgnoreCase(name)) {
                return belt;
            }
        }
        throw new ResourceNotFoundException("Nenhuma constante enum com o nome " + name);
    }
}