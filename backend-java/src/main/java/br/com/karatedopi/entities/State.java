package br.com.karatedopi.entities;

import br.com.karatedopi.entities.enums.StateAbbreviation;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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

	@OneToMany(mappedBy = "state", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Builder.Default
	private Set<City> cities = new HashSet<>();

	public City getCityByName(String cityName) {
		return this.cities.stream().filter(c ->
			c.getName().equalsIgnoreCase(cityName)).findFirst().orElseThrow(() ->
			new ResourceNotFoundException("No city was found for this state"));
	}
}