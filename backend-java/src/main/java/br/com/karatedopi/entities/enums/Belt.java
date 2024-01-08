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

	WHITE("White Belt"),
	YELLOW("Yellow Belt"),
	RED("Red Belt"),
	ORANGE("Orange Belt"),
	GREEN("Green Belt"),
	PURPLE("Purple Belt"),
	BROWN("Brown Belt"),
	BLACK_01("Shodan - 1st-degree black belt"),
	BLACK_02("Nidan - 2nd-degree black belt"),
	BLACK_03("Sandan - 3rd-degree black belt"),
	BLACK_04("Yondan - 4th-degree black belt"),
	BLACK_05("Godan - 5th-degree black belt"),
	BLACK_06("Rokudan - 6th-degree black belt"),
	BLACK_07("Shichidan - 7th-degree black belt"),
	BLACK_08("Hachidan - 8th-degree black belt"),
	BLACK_09("Kudan - 9th-degree black belt"),
	BLACK_10("Judan - 10th-degree black belt");

	private final String description;

	public static Belt getValueByDescriptionOrValue(String name) {
		for (Belt belt : values()) {
			if (belt.getDescription().equalsIgnoreCase(name) || belt.toString().equalsIgnoreCase(name)) {
				return belt;
			}
		}
		throw new ResourceNotFoundException("No enum constant with name " + name);
	}

	public static Belt getValueByDescription(String name) {
		for (Belt belt : values()) {
			if (belt.getDescription().equalsIgnoreCase(name)) {
				return belt;
			}
		}
		throw new ResourceNotFoundException("No enum constant with name " + name);
	}

	public static Belt getValueByValue(String value) {
		for (Belt belt : values()) {
			if (belt.toString().equalsIgnoreCase(value)) {
				return belt;
			}
		}
		throw new ResourceNotFoundException("No enum constant with name " + value);
	}

	public static int getIndexByValue(Belt enumValue) {
		Belt[] belts = Belt.values();
		for (int i = 0; i < belts.length; i++) {
			if (belts[i] == enumValue) {
				return i;
			}
		}
		throw new ResourceNotFoundException("Enum constant not found: " + enumValue);
	}
}
