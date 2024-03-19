package br.com.karatedopi.entities;

import br.com.karatedopi.entities.enums.OrganizationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;

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
@Table(name = "association")
public class Association {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessName;
    private String tradeName;
    private String associationAbbreviation;
    private LocalDate foundationDate;
    private String ein;
    private String email;

    @ElementCollection(fetch=FetchType.EAGER)
    private Set<String> phoneNumbers;

    @Enumerated(EnumType.STRING)
    private OrganizationStatus status;

    @ManyToOne
    private Federation federation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_user_id")
    private Profile president;

    @OneToMany(mappedBy = "association", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<Profile> affiliates = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;

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
        Association that = (Association) o;
        return businessName.equals(that.businessName) && tradeName.equals(that.tradeName) &&
                associationAbbreviation.equals(that.associationAbbreviation) &&
                foundationDate.equals(that.foundationDate) && ein.equals(that.ein) && email.equals(that.email) &&
                Objects.equals(phoneNumbers, that.phoneNumbers) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessName, tradeName, associationAbbreviation, foundationDate, ein, email, phoneNumbers, status);
    }
}