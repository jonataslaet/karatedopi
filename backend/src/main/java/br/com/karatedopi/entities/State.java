package br.com.karatedopi.entities;

import br.com.karatedopi.entities.enums.StateAbbreviation;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "state")
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private StateAbbreviation stateAbbreviation;

	@OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
	private Set<City> cities;
}