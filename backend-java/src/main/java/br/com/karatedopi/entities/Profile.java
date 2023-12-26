package br.com.karatedopi.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.MapsId;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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

	@ManyToOne
	private Tournament tournament;

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
