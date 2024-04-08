package br.com.karatedopi.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.OneToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.CascadeType;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    private Long id;
    private String fullname;
    private String father;
    private String mother;

    @ManyToOne
    private Address address;

    @ManyToOne
    private Association association;

    private String bloodType;
    private String itin;
    private String nid;
    private LocalDate birthday;

    @ElementCollection(fetch=FetchType.EAGER)
    private Set<String> phoneNumbers;

    @OneToMany(mappedBy = "id.profile", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ProfileGraduation> profileGraduations = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @OneToMany(mappedBy = "id.profile", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<TournamentParticipation> tournaments = new HashSet<>();

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
        Profile profile = (Profile) o;
        return fullname.equals(profile.fullname) && father.equals(profile.father) && mother.equals(profile.mother) && bloodType.equals(profile.bloodType) && itin.equals(profile.itin) && nid.equals(profile.nid) && birthday.equals(profile.birthday) && phoneNumbers.equals(profile.phoneNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullname, father, mother, bloodType, itin, nid, birthday, phoneNumbers);
    }
}