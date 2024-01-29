package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Profile;
import br.com.karatedopi.entities.ProfileGraduation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProfileGraduationRepository extends JpaRepository<ProfileGraduation, Long> {

    @Query("""
            SELECT profileGraduation FROM ProfileGraduation profileGraduation
            WHERE LOWER(profileGraduation.id.graduation.belt) LIKE LOWER(:belt)
            """)
    Set<ProfileGraduation> findProfileGraduationsByBelt(@Param("belt") String belt);

    @Query("""
            SELECT profileGraduation FROM ProfileGraduation profileGraduation
            WHERE profileGraduation.id.profile = :profile
            """)
    Set<ProfileGraduation> findProfileGraduationsByProfile(@Param("profile") Profile profile);

    @Query("""
            SELECT profileGraduation FROM ProfileGraduation profileGraduation
            WHERE profileGraduation.id.profile = :profile AND LOWER(profileGraduation.id.graduation.belt) LIKE LOWER(:status)
            """)
    ProfileGraduation findProfileGraduationByProfileAndBelt(@Param("profile") Profile profile, @Param("status") String status);

    @Query("""
            SELECT profileGraduation FROM ProfileGraduation profileGraduation
            WHERE profileGraduation.id.profile in (:profiles)
            """)
    Set<ProfileGraduation> findProfileGraduationsByProfiles(@Param("profiles") Set<Profile> profiles);
}