package br.com.karatedopi.services;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.karatedopi.controllers.cadastros.CadastroEstado;
import br.com.karatedopi.controllers.dtos.EstadoDto;
import br.com.karatedopi.controllers.dtos.MunicipioDto;
import br.com.karatedopi.domain.Estado;
import br.com.karatedopi.repositories.EstadoRepository;
import br.com.karatedopi.repositories.MunicipioRepository;

@Service
public class EstadoService {
	@Autowired
	EstadoRepository er;
	
	@Autowired
	MunicipioRepository mr;
	
	//Retorna todos os estados
	public ResponseEntity<List<EstadoDto>> retornaEstados(){
		List<EstadoDto> estadosEncontrados = EstadoDto.estadosDto(er.findAll());
		return ResponseEntity.ok().body(estadosEncontrados);
	}
	
	public ResponseEntity<EstadoDto> criaEstado(CadastroEstado cadastroEstado){
		
		//Monta o estado
		Estado estado = new Estado(cadastroEstado.getDescricao(), cadastroEstado.getCodigoIBGE(), cadastroEstado.getUf());
		
		//Adiciona o estado ao estado
//		estado.getEstados().add(municipio);
		
		//Salva o estado
		er.save(estado);
		
		//Cria o dto do estado
		EstadoDto municipioDto = new EstadoDto(estado);
		
		// Gera o link do novo estado adicionado
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(municipioDto.getId())
				.toUri();

		// Envia na resposta o código de criação e no header envia o link gerado do novo
		// estado
		return ResponseEntity.created(location).build();
	}
	
	//Retorna todos os municípios do estado
	public ResponseEntity<List<MunicipioDto>> listaMunicipiosDoEstado(Long id){
		Estado estadoEncontrado = er.findById(id).get();
		List<MunicipioDto> municipiosEncontrados = MunicipioDto.municipiosDto(mr.findAllByEstado(estadoEncontrado));
		return ResponseEntity.ok().body(municipiosEncontrados);
	}
}
