package br.com.karatedopi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "address", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<Profile> residents = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return street.equals(address.street) && number.equals(address.number) &&
                zipCode.equals(address.zipCode) && neighbourhood.equals(address.neighbourhood);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, number, zipCode, neighbourhood);
    }
}