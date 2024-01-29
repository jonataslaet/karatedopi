package br.com.karatedopi.entities;

import br.com.karatedopi.entities.enums.TournamentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

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
@Table(name = "tournament")
public class Tournament {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private TournamentStatus status;

	private LocalDateTime eventDate;

	@OneToOne
	private Address address;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_tournament_participant",
			joinColumns = @JoinColumn(name = "tournament_id"),
			inverseJoinColumns = @JoinColumn(name = "participant_id"))
	@Builder.Default
	private Set<Profile> participants = new HashSet<>();
}
