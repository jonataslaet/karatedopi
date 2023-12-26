package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    @Query("SELECT DISTINCT tournament FROM Tournament tournament WHERE LOWER(tournament.status) LIKE LOWER(CONCAT('%',:status,'%'))")
    Page<Tournament> findAllTournamentsByStatus(@Param("status") String status, Pageable pagination);

    @Query("SELECT DISTINCT tournament FROM Tournament tournament")
    Page<Tournament> findAllTournaments(Pageable pagination);
}
