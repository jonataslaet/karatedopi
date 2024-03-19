package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.City;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
    private Long id;
    private String name;
    private StateDTO state;

    public static CityDTO getCityDTO(City city) {
        return CityDTO.builder()
                .id(city.getId())
                .name(city.getName())
                .state(StateDTO.getStateDTO(city.getState()))
                .build();
    }
}
