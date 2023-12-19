package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.enums.UserStatus;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
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
public class UserReadDTO {

	private Long id;
	private String firstname;
	private String lastname;
	private String authority;
	private String email;
	private UserStatus status;

	public static UserReadDTO getUserReadDTO(User user) {
		return UserReadDTO.builder()
				.id(user.getId())
				.authority(user.getRoles().stream().map(Role::getAuthority).findFirst().orElseThrow(() ->
						new ResourceNotFoundException("Role not found")))
				.email(user.getEmail())
				.firstname(user.getFirstname())
				.lastname(user.getLastname())
				.status(user.getStatus())
				.build();
	}
}
