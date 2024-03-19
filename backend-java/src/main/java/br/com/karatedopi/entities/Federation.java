package br.com.karatedopi.entities;

import br.com.karatedopi.entities.enums.OrganizationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "federation")
public class Federation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessName;
    private String tradeName;
    private String federationAbbreviation;
    private LocalDate foundationDate;
    private String ein;
    private String email;

    @ElementCollection(fetch=FetchType.EAGER)
    private Set<String> phoneNumbers;

    @Enumerated(EnumType.STRING)
    private OrganizationStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    private Profile president;

    @OneToMany(mappedBy = "federation")
    @Builder.Default
    private Set<Association> associations = new HashSet<>();

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedOn = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Federation that = (Federation) o;
        return businessName.equals(that.businessName) && tradeName.equals(that.tradeName) &&
                federationAbbreviation.equals(that.federationAbbreviation) &&
                foundationDate.equals(that.foundationDate) && ein.equals(that.ein) &&
                Objects.nonNull(email) && email.equals(that.email) && phoneNumbers.equals(that.phoneNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessName, tradeName, federationAbbreviation, foundationDate, ein, phoneNumbers);
    }
}