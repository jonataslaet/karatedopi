package br.com.karatedopi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.karatedopi.domain.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{

}
