package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.utils.Utils;
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
public class TournamentParticipantDTO {
	private Long id;
	private String fullname;
	private Integer age;

	public static TournamentParticipantDTO getTournamentParticipantDTO(Profile profile) {
		return TournamentParticipantDTO.builder()
				.id(profile.getId())
				.fullname(profile.getFullname())
				.age(Utils.getAgeByBirthday(profile.getBirthday()))
				.build();
	}
}

