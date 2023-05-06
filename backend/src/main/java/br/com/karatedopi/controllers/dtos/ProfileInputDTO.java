package br.com.karatedopi.controllers.dtos;

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
public class ProfileInputDTO {

	private Long id;
	private String fullname;
	private String father;
	private String mother;
	private String zipCode;
	private String address;
	private String number;
	private String neighbourhood;
	private String city;
	private String state;
	private String bloodType;
	private String cpf;
	private String rg;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")  
	private LocalDate birthday;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm")
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime creationDate = LocalDateTime.now();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm")
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime lastUpdate = LocalDateTime.now();

	private Set<String> phoneNumbers = new HashSet<>();

}
