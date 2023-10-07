package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

	@Query("SELECT address FROM Address address WHERE LOWER(address.city.name) LIKE LOWER(CONCAT('%',:cityName,'%'))")
	Page<Address> findAddressesByCityName(String cityName, Pageable pagination);
}
