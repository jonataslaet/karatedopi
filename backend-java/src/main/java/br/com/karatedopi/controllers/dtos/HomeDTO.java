package br.com.karatedopi.controllers.dtos;

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
public class HomeDTO {
	private String firstname;
	private String lastname;
}
