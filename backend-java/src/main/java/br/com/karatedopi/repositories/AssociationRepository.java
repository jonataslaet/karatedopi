package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Association;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {

    @Query("SELECT association FROM Association association WHERE association.federation.id = :federationId")
    Page<Association> findAllByFederationId(@Param("federationId") Long federationId, Pageable pagination);

    @Query("SELECT association FROM Association association WHERE UPPER(association.associationAbbreviation) LIKE UPPER(:associationAbbreviation)")
    Association findAssociationByAbbreviation(@Param("associationAbbreviation") String associationAbbreviation);

    @Query("SELECT COUNT(a) FROM Association a WHERE UPPER(a.associationAbbreviation) LIKE UPPER(:abbreviation)")
    Long countByAbbreviation(@Param("abbreviation") String abbreviation);
}
