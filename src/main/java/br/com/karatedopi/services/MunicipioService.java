package br.com.karatedopi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.karatedopi.controllers.dtos.MunicipioDTO;
import br.com.karatedopi.domain.Municipio;
import br.com.karatedopi.domain.enums.EstadoEnum;
import br.com.karatedopi.repositories.MunicipioRepository;

@Service
public class MunicipioService {
	
	@Autowired
	private MunicipioRepository municipioRepository;
	
	public Page<MunicipioDTO> retornaMunicipios(EstadoEnum uf, Pageable paginacao){
		if (uf == null) {
			Page<Municipio> municipios = municipioRepository.findAll(paginacao);
			return municipios.map(MunicipioDTO::new);
		}
		Page<Municipio> municipios = municipioRepository.findAllByEstadoUf(uf, paginacao);
		return municipios.map(MunicipioDTO::new);
	}

}

