package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.City;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.Tournament;
import br.com.karatedopi.utils.Utils;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentParticipantDTO {

	private String fullname;
	private Integer age;

	public static TournamentParticipantDTO getTournamentParticipantDTO(Profile profile) {
		return TournamentParticipantDTO.builder()
				.fullname(profile.getFullname())
				.age(Utils.getAgeByBirthday(profile.getBirthday()))
				.build();
	}
}

