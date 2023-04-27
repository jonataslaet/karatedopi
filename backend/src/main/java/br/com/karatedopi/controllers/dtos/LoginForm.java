package br.com.karatedopi.controllers.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

	private String email;
	String password;
}
