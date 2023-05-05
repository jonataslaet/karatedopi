package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Profile;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateResponseDTO {

	private Long id;
	private String firstname;
	private String lastname;
	private String fullname;
	private String father;
	private String mother;
	private String hometown;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")  
	private LocalDate birthday;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm")
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime creationDate = LocalDateTime.now();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm")
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime lastUpdate = LocalDateTime.now();
	
	private String cpf;
	private String rg;
	private Set<String> phoneNumbers = new HashSet<>();

	public static ProfileUpdateResponseDTO getProfileUpdateResponseDTO(Profile profile) {
		return ProfileUpdateResponseDTO.builder()
				.id(profile.getId())
				.rg(profile.getRg())
				.cpf(profile.getCpf())
				.firstname(profile.getFirstname())
				.lastname(profile.getLastname())
				.fullname(profile.getFullname())
				.father(profile.getFather())
				.mother(profile.getMother())
				.phoneNumbers(profile.getPhoneNumbers())
				.hometown(profile.getHometown())
				.birthday(profile.getBirthday())
				.creationDate(profile.getCreatedOn())
				.lastUpdate(profile.getUpdatedOn())
				.build();
	}
}
