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

	WHITE(1, "White Belt"),
	YELLOW(2, "Yellow Belt"),
	RED(3, "Red Belt"),
	ORANGE(4, "Orange Belt"),
	GREEN(5, "Green Belt"),
	PURPLE(6, "Purple Belt"),
	BROWN(7, "Brown Belt"),
	BLACK_01(8, "Shodan - 1st-degree black belt"),
	BLACK_02(9, "Nidan - 2nd-degree black belt"),
	BLACK_03(10, "Sandan - 3rd-degree black belt"),
	BLACK_04(11, "Yondan - 4th-degree black belt"),
	BLACK_05(12, "Godan - 5th-degree black belt"),
	BLACK_06(13, "Rokudan - 6th-degree black belt"),
	BLACK_07(14, "Shichidan - 7th-degree black belt"),
	BLACK_08(15, "Hachidan - 8th-degree black belt"),
	BLACK_09(16, "Kudan - 9th-degree black belt"),
	BLACK_10(17, "Judan - 10th-degree black belt");

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

	public static Belt getValueByDescription(String name) {
		for (Belt belt : values()) {
			if (belt.getDescription().equalsIgnoreCase(name)) {
				return belt;
			}
		}
		throw new ResourceNotFoundException("Nenhuma constante enum com o nome " + name);
	}

	public static Belt getValueByValue(String value) {
		for (Belt belt : values()) {
			if (belt.toString().equalsIgnoreCase(value)) {
				return belt;
			}
		}
		throw new ResourceNotFoundException("Nenhuma constante enum com o nome " + value);
	}

	public static int getIndexByValue(Belt enumValue) {
		Belt[] belts = Belt.values();
		for (int i = 0; i < belts.length; i++) {
			if (belts[i] == enumValue) {
				return i;
			}
		}
		throw new ResourceNotFoundException("Nenhuma constante enum com o nome " + enumValue);
	}
}
