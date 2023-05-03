package br.com.karatedopi.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {

	@Id
	private Long id;
	private String firstname;
	private String lastname;
	private String fullname;
	private String father;
	private String mother;
	private String hometown;
	private LocalDate birthday;
	private String cpf;
	private String rg;

	@ElementCollection(fetch=FetchType.EAGER)
	private Set<String> phoneNumbers;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private User user;

	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@PrePersist
	public void prePersist() {
		createdOn = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		updatedOn = LocalDateTime.now();
	}
}
