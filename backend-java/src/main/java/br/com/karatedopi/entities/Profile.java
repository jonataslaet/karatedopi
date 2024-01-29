package br.com.karatedopi.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.OneToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
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

	@OneToMany(mappedBy = "id.profile")
	@Builder.Default
	private Set<ProfileGraduation> profileGraduations = new HashSet<>();

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private User user;

	@ManyToMany(mappedBy = "participants")
	@Builder.Default
	private Set<Tournament> tournaments = new HashSet<>();

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
