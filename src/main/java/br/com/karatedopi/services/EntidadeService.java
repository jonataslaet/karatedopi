package br.com.karatedopi.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.karatedopi.controllers.cadastros.CadastroEntidade;
import br.com.karatedopi.controllers.dtos.EntidadeDto;
import br.com.karatedopi.controllers.errors.ObjectNotFoundException;
import br.com.karatedopi.domain.Entidade;
import br.com.karatedopi.repositories.EntidadeRepository;
import br.com.karatedopi.repositories.LocalidadeRepository;


@Service
public class EntidadeService {
	@Autowired
	EntidadeRepository er;
	
	@Autowired
	LocalidadeRepository lr;
	
	// Retorna todas as entidades
	public ResponseEntity<List<EntidadeDto>> retornaEntidades() {
		List<EntidadeDto> locaisEncontrados = EntidadeDto.entidadesDto(er.findAll());
		return ResponseEntity.ok().body(locaisEncontrados);
	}

	// Retorna um localidade específico
	public ResponseEntity<EntidadeDto> retornaEntidade(Long id) {
		Optional<Entidade> entidade = er.findById(id);
		if (!entidade.isPresent()) {
			throw new ObjectNotFoundException("Entidade não encontrada");
		}
		EntidadeDto entidadadeDto = new EntidadeDto(entidade.get());
		return ResponseEntity.ok().body(entidadadeDto);
	}

	// Cadastra um novo local
	public ResponseEntity<EntidadeDto> criaEntidade(CadastroEntidade cadastroEntidade) {

		// Monta o entidade
		Entidade entidade = new Entidade(cadastroEntidade.getNome(), cadastroEntidade.getSigla(), cadastroEntidade.getDataFundacao(), cadastroEntidade.getContatos());
		entidade.setLocalidade(cadastroEntidade.getLocalidade());
		
		// Salva a entidade
		er.save(entidade);

		// Cria o dto da entidade
		EntidadeDto entidadeDto = new EntidadeDto(entidade);

		// Gera o link do novo user adicionado
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entidadeDto.getId())
				.toUri();

		// Envia na resposta o código de criação e no header envie o link gerado do novo
		// local
		return ResponseEntity.created(location).build();
	}
}
