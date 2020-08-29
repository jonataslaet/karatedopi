package br.com.karatedopi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.karatedopi.domain.Estado;
import br.com.karatedopi.domain.Municipio;

public interface MunicipioRepository extends JpaRepository<Municipio, Long>{
	List<Municipio> findAllByEstado(Estado id);
}
