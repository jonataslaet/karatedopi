package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.City;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
	private Long id;
	private String name;

	public static CityDTO getCityDTO(City city) {
		return CityDTO.builder().id(city.getId()).name(city.getName()).build();
	}
}

