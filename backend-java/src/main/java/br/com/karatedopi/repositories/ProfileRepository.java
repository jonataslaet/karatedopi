package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>{

	@Query("SELECT prof FROM Profile prof WHERE (LOWER(prof.city) like LOWER(CONCAT('%', :hometown, '%'))) " +
			"or (LOWER(prof.state) like LOWER(CONCAT('%', :hometown, '%')))")
	Page<Profile> findAllByHometown(String hometown, Pageable pagination);

}
