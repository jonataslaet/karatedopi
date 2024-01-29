package br.com.karatedopi.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Embeddable
public class ProfileGraduationPK {

    @ManyToOne
    @JoinColumn(name="profile_user_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name="graduation_id")
    private Graduation graduation;
}
