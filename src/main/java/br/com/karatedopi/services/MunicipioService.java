package br.com.karatedopi.services;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.karatedopi.controllers.cadastros.CadastroMunicipio;
import br.com.karatedopi.controllers.dtos.LocalidadeDto;
import br.com.karatedopi.controllers.dtos.MunicipioDto;
import br.com.karatedopi.domain.Municipio;
import br.com.karatedopi.repositories.LocalidadeRepository;
import br.com.karatedopi.repositories.MunicipioRepository;

@Service
public class MunicipioService {
	@Autowired
	MunicipioRepository mr;
	
	@Autowired
	LocalidadeRepository lr;
	
	//Retorna todos os municípios
	public ResponseEntity<List<MunicipioDto>> retornaMunicipios(){
		List<MunicipioDto> municipiosEncontrados = MunicipioDto.municipiosDto(mr.findAll());
		return ResponseEntity.ok().body(municipiosEncontrados);
	}
	
	public ResponseEntity<MunicipioDto> criaMunicipio(CadastroMunicipio cadastroMunicipio){
		
		//Captura o estado cadastrado
//		Estado estado = null;
		
		//Monta o município
		Municipio municipio = new Municipio(cadastroMunicipio.getDescricao(), cadastroMunicipio.getCodigoIBGE(), cadastroMunicipio.getCodigoSIAFI());
		
		//Adiciona o município ao estado
//		estado.getMunicipios().add(municipio);
		
		//Salva o município
		mr.save(municipio);
		
		//Cria o dto do município
		MunicipioDto municipioDto = new MunicipioDto(municipio);
		
		// Gera o link do novo user adicionado
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(municipioDto.getId())
				.toUri();

		// Envia na resposta o código de criação e no header envie o link gerado do novo
		// perfil
		return ResponseEntity.created(location).build();
	}
	
	//Retorna todos as localidades do município
	public ResponseEntity<List<LocalidadeDto>> listaLocalidadesDoMunicipio(Long id){
		Municipio municipioEncontrado = mr.findById(id).get();
		List<LocalidadeDto> localidadesEncontradas = LocalidadeDto.locaisDto(lr.findAllByMunicipio(municipioEncontrado));
		return ResponseEntity.ok().body(localidadesEncontradas);
	}
}
