package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.authority = :name OR r.authority like UPPER(CONCAT('ROLE_', :name))")
    Optional<Role> findByName(@Param("name") String name);
}
