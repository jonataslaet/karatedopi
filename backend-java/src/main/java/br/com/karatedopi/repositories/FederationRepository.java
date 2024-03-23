package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Federation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FederationRepository extends JpaRepository<Federation, Long> {

    @Query("""
        SELECT federation FROM Federation federation 
        WHERE UPPER(federation.federationAbbreviation) LIKE UPPER(:federationAbbreviation)
    """)
    Optional<Federation> findByAbbreviation(@Param("federationAbbreviation") String federationAbbreviation);

    @Query("""
        SELECT federation FROM Federation federation 
        WHERE (:search IS NULL OR TRIM(CAST(:search AS text)) = '' OR
        UPPER(federation.businessName) like UPPER(CONCAT('%', CAST(:search AS text), '%')) OR 
        UPPER(federation.tradeName) like UPPER(CONCAT('%', CAST(:search AS text), '%')) OR  
        UPPER(federation.federationAbbreviation) like UPPER(CONCAT('%', CAST(:search AS text), '%')) OR  
        UPPER(federation.ein) like UPPER(CONCAT('%', CAST(:search AS text), '%')) OR  
        UPPER(federation.email) like UPPER(CONCAT('%', CAST(:search AS text), '%'))) AND 
        (:status IS NULL OR TRIM(CAST(:status AS text)) = '' OR
        LOWER(federation.status) like LOWER(CONCAT('%', CAST(:status AS text), '%')))
        """)
    Page<Federation> findAllBySearchContent(@Param("search") String search, @Param("status") String status, Pageable pagination);
}
