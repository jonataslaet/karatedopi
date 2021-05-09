package br.com.karatedopi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.karatedopi.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{

}
