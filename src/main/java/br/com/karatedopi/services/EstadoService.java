package br.com.karatedopi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.karatedopi.controllers.dtos.EstadoDTO;
import br.com.karatedopi.controllers.dtos.MunicipioDTO;
import br.com.karatedopi.domain.Estado;
import br.com.karatedopi.repositories.EstadoRepository;
import br.com.karatedopi.repositories.MunicipioRepository;

@Service
public class EstadoService {
	@Autowired
	EstadoRepository estadoRepository;
	
	@Autowired
	MunicipioRepository municipioRepository;
	
	public ResponseEntity<List<EstadoDTO>> retornaEstados(){
		List<EstadoDTO> estadosEncontrados = EstadoDTO.estadosDto(estadoRepository.findAll());
		return ResponseEntity.ok().body(estadosEncontrados);
	}
	
	public ResponseEntity<List<MunicipioDTO>> listaMunicipiosDoEstado(Long id){
		Estado estadoEncontrado = estadoRepository.findById(id).get();
		List<MunicipioDTO> municipiosEncontrados = MunicipioDTO.municipiosDto(municipioRepository.findAllByEstado(estadoEncontrado));
		return ResponseEntity.ok().body(municipiosEncontrados);
	}
}

