package br.com.karatedopi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.karatedopi.domain.Estado;
import br.com.karatedopi.domain.Municipio;
import br.com.karatedopi.domain.enums.EstadoEnum;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long>{

	List<Municipio> findAllByEstado(Estado estadoEncontrado);
	
	Page<Municipio> findAllByEstadoUf(EstadoEnum siglaDoEstado, Pageable paginacao);

}
