package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Graduation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraduationRepository extends JpaRepository<Graduation, Long>{

}
