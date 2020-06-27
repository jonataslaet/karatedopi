package br.com.karatedopi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.karatedopi.domain.Entidade;

public interface EntidadeRepository extends JpaRepository<Entidade, Long>{

}
