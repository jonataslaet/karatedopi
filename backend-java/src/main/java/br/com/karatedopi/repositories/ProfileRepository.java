package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT prof FROM Profile prof WHERE LOWER(prof.fullname) like LOWER(CONCAT('%', :search, '%')) ")
    Page<Profile> findAllByFullname(@Param("search") String search, Pageable pagination);

}
