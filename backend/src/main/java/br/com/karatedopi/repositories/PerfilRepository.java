package br.com.karatedopi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.karatedopi.domain.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{

	Page<Perfil> findAllByNaturalidade(String naturalidade, Pageable paginacao);

}
