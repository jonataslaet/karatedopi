package br.com.karatedopi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

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
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	private String street;
	private String number;
	private String zipCode;
	private String neighbourhood;

	@ManyToOne
	private City city;

	@OneToMany(mappedBy = "address")
	@Builder.Default
	private Set<Profile> residents = new HashSet<>();
}
