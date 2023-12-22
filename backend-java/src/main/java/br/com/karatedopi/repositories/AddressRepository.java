package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

	@Query("SELECT address FROM Address address WHERE LOWER(address.city.name) LIKE LOWER(CONCAT('%',:cityName,'%'))")
	Page<Address> findAddressesByCityName(@Param("cityName") String cityName, Pageable pagination);

	@Query("SELECT address FROM Address address WHERE address.city.id in (:cityIds)")
	Page<Address> findAddressesByCities(@Param("cityIds") List<Long> cityIds, Pageable pagination);
}
