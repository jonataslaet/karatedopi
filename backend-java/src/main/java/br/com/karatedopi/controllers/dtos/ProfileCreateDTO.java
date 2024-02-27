package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.services.exceptions.ForbiddenOperationException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCreateDTO {

	private Long id;
	private String fullname;
	private String father;
	private String mother;
	private String zipCode;
	private String street;
	private String number;
	private String neighbourhood;
	private String city;
	private String state;
	private String bloodType;
	private String itin;
	private String nid;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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

	public void setBirthday(String birthday) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
			this.birthday = LocalDate.parse(birthday, formatter);
		} catch (DateTimeException e) {
			throw new ForbiddenOperationException(e.getLocalizedMessage());
		}
	}

}
