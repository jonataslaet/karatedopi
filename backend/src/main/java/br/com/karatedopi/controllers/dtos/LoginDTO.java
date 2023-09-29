package br.com.karatedopi.controllers.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

	private String email;
	private String password;
}
