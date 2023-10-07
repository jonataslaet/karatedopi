package br.com.karatedopi.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
	private String fullname;
	private String father;
	private String mother;

	@ManyToOne
	private Address address;

	private String bloodType;
	private String cpf;
	private String rg;
	private LocalDate birthday;

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
