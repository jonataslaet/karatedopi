package br.com.karatedopi.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.karatedopi.controllers.cadastros.CadastroLocalidade;
import br.com.karatedopi.controllers.dtos.LocalidadeDto;
import br.com.karatedopi.controllers.errors.ObjectNotFoundException;
import br.com.karatedopi.domain.Localidade;
import br.com.karatedopi.repositories.LocalidadeRepository;
import br.com.karatedopi.repositories.MunicipioRepository;

@Service
public class LocalidadeService {
	@Autowired
	LocalidadeRepository lr;

	@Autowired
	MunicipioRepository mr;
	
	// Retorna todas as localidadeidades
	public ResponseEntity<List<LocalidadeDto>> retornaLocais() {
		List<LocalidadeDto> locaisEncontrados = LocalidadeDto.locaisDto(lr.findAll());
		return ResponseEntity.ok().body(locaisEncontrados);
	}

	// Retorna um localidade específico
	public ResponseEntity<LocalidadeDto> retornaLocalidade(Long id) {
		Optional<Localidade> localidade = lr.findById(id);
		if (!localidade.isPresent()) {
			throw new ObjectNotFoundException("Local não encontrado");
		}
		LocalidadeDto localidadeDto = new LocalidadeDto(localidade.get());
		return ResponseEntity.ok().body(localidadeDto);
	}

	// Cadastra um novo local
	public ResponseEntity<LocalidadeDto> criaLocalidade(CadastroLocalidade cadastroLocalidade) {

		// Monta o local
		Localidade local = new Localidade(cadastroLocalidade.getRua(), cadastroLocalidade.getNumero(), cadastroLocalidade.getCep(), cadastroLocalidade.getBairro());

		// Salva o local
		lr.save(local);
		
		// Cria o dto do local
		LocalidadeDto localDto = new LocalidadeDto(local);

		// Gera o link do novo user adicionado
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(localDto.getId())
				.toUri();

		// Envia na resposta o código de criação e no header envie o link gerado do novo
		// local
		return ResponseEntity.created(location).build();
	}
}
