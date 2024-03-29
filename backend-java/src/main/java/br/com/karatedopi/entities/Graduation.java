package br.com.karatedopi.entities;

import br.com.karatedopi.entities.enums.Belt;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "graduation")
public class Graduation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Enumerated(EnumType.STRING)
	private Belt belt;

	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@OneToMany(mappedBy="id.graduation")
	@Builder.Default
	private Set<ProfileGraduation> gradedProfiles = new HashSet<>();

	@PrePersist
	public void prePersist() {
		createdOn = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		updatedOn = LocalDateTime.now();
	}
}
