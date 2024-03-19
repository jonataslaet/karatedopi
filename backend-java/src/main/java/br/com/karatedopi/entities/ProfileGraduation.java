package br.com.karatedopi.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.EmbeddedId;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile_graduation")
public class ProfileGraduation {

    @EmbeddedId
    @Builder.Default
    @EqualsAndHashCode.Include
    private ProfileGraduationPK id = new ProfileGraduationPK();

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    public void setProfile(Profile profile) {
        this.id.setProfile(profile);
    }

    public void setGraduation(Graduation graduation) {
        this.id.setGraduation(graduation);
    }

    public Profile getProfile() {
        return this.id.getProfile();
    }

    public Graduation getGraduation() {
        return this.id.getGraduation();
    }

    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedOn = LocalDateTime.now();
    }
}