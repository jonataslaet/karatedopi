package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Graduation;
import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.enums.Belt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileReadDTO {

	private Long id;
	private String fullname;
	private String father;
	private String mother;
	private AddressDTO address;
	private String bloodType;
	private String cpf;
	private String rg;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")  
	private LocalDate birthday;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm")
	@JsonProperty(access = Access.READ_ONLY)
	@Builder.Default
	private LocalDateTime creationDate = LocalDateTime.now();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm")
	@JsonProperty(access = Access.READ_ONLY)
	@Builder.Default
	private LocalDateTime lastUpdate = LocalDateTime.now();

	@Builder.Default
	private Set<String> phoneNumbers = new HashSet<>();

	private Belt currentBelt;

	public static ProfileReadDTO getProfileReadDTO(Profile profile) {
		return ProfileReadDTO.builder()
				.id(profile.getId())
				.fullname(profile.getFullname())
				.mother(profile.getMother())
				.father(profile.getFather())
				.address(AddressDTO.getAddressDTO(profile.getAddress()))
				.bloodType(profile.getBloodType())
				.birthday(profile.getBirthday())
				.cpf(profile.getCpf())
				.rg(profile.getRg())
				.phoneNumbers(profile.getPhoneNumbers())
				.currentBelt(profile.getGraduations().stream()
						.max(Comparator.comparing(Graduation::getCreatedOn))
						.map(Graduation::getBelt)
						.orElse(null))
				.creationDate(profile.getCreatedOn())
				.lastUpdate(profile.getUpdatedOn())
				.build();
	}
}
