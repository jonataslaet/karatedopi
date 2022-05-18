package br.com.karatedopi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.karatedopi.domain.Papel;

@Repository
public interface PapelRepository extends JpaRepository<Papel, Long> {

}
