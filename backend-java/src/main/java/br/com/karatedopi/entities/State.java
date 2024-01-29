package br.com.karatedopi.entities;

import br.com.karatedopi.entities.enums.StateAbbreviation;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "state")
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
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
			new ResourceNotFoundException("Nenhuma cidade foi encontrada para este estado"));
	}
}