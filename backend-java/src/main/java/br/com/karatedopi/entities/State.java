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

import java.util.HashSet;
import java.util.Objects;
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
                new ResourceNotFoundException("Nenhuma cidade foi encontrada para este estado"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return id.equals(state.id) && name.equals(state.name) && stateAbbreviation == state.stateAbbreviation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stateAbbreviation);
    }
}