package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.State;
import br.com.karatedopi.entities.enums.StateAbbreviation;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StateDTO {
	private Long id;
	private String name;
	private StateAbbreviation stateAbbreviation;

	public static StateDTO getStateDTO(State state) {
		return StateDTO.builder().id(state.getId()).name(state.getName()).stateAbbreviation(state.getStateAbbreviation()).build();
	}
}
