package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Graduation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GraduationRepository extends JpaRepository<Graduation, Long> {

    @Query("SELECT graduation FROM Graduation graduation WHERE LOWER(graduation.belt) LIKE LOWER(:belt)")
    Graduation findGraduationByBelt(@Param("belt") String belt);
}
