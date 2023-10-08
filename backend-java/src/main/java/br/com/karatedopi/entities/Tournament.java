package br.com.karatedopi.entities;

import br.com.karatedopi.entities.enums.TournamentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tournament")
public class Tournament {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private TournamentStatus status;

	private LocalDateTime eventDate;

	@OneToOne
	private Address address;

	@OneToMany
	@Builder.Default
	private List<Profile> participants = new ArrayList<>();
}
