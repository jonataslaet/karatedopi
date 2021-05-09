package br.com.karatedopi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.karatedopi.controllers.dtos.MunicipioDTO;
import br.com.karatedopi.repositories.MunicipioRepository;

@Service
public class MunicipioService {
	@Autowired
	MunicipioRepository municipioRepository;
	
	public ResponseEntity<List<MunicipioDTO>> retornaMunicipios(){
		List<MunicipioDTO> municipiosEncontrados = MunicipioDTO.municipiosDto(municipioRepository.findAll());
		return ResponseEntity.ok().body(municipiosEncontrados);
	}

}

