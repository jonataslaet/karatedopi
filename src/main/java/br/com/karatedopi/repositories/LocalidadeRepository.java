package br.com.karatedopi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.karatedopi.domain.Localidade;
import br.com.karatedopi.domain.Municipio;

public interface LocalidadeRepository extends JpaRepository<Localidade, Long>{
	List<Localidade> findAllByMunicipio(Municipio id);
}
