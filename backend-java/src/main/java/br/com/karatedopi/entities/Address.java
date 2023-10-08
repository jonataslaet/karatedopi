package br.com.karatedopi.entities;

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
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
