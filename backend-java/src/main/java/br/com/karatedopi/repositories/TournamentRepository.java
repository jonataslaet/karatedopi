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

    @Query("""
        SELECT tournament FROM Tournament tournament
        WHERE (:search IS NULL OR TRIM(CAST(:search AS text)) = '' OR
        LOWER(tournament.name) like LOWER(CONCAT('%', CAST(:search AS text), '%'))) AND (:status IS NULL OR TRIM(CAST(:status AS text)) = '' OR
        LOWER(tournament.status) like LOWER(CONCAT('%', CAST(:status AS text), '%')))
        """)
    Page<Tournament> findAllBySearchContent(
            @Param("search") String search, @Param("status") String status, Pageable pagination);
}
