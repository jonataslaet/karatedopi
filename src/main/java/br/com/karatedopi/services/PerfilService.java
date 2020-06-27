package br.com.karatedopi.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.karatedopi.controllers.cadastros.CadastroPerfil;
import br.com.karatedopi.controllers.dtos.PerfilDto;
import br.com.karatedopi.controllers.errors.ObjectNotFoundException;
import br.com.karatedopi.domain.Perfil;
import br.com.karatedopi.domain.Usuario;
import br.com.karatedopi.repositories.PerfilRepository;
import br.com.karatedopi.repositories.UsuarioRepository;

@Service
public class PerfilService {
	@Autowired
	PerfilRepository pr;

	@Autowired
	UsuarioRepository ur;

	// Retorna todos os perfis
	public ResponseEntity<List<PerfilDto>> retornaPerfis() {
		List<PerfilDto> perfisEncontrados = PerfilDto.perfisDto(pr.findAll());
		return ResponseEntity.ok().body(perfisEncontrados);
	}

	// Retorna um perfil específico
	public ResponseEntity<PerfilDto> retornaPerfil(Long id) {
		Optional<Perfil> perfil = pr.findById(id);
		if (!perfil.isPresent()) {
			throw new ObjectNotFoundException("O perfil não foi encontrado");
		}
		PerfilDto perfilDto = new PerfilDto(perfil.get());
		return ResponseEntity.ok().body(perfilDto);
	}

	// Cadastra um novo perfil
	public ResponseEntity<PerfilDto> criarPerfil(CadastroPerfil cadastroPerfil) {

		// Captura o usuário logado
		Usuario usuarioLogado = ur.findById(2L).get();

		// Monta o Perfil
		Perfil perfil = new Perfil(cadastroPerfil.getNome(), cadastroPerfil.getNomeCompleto());

		// Seta o usuário logado
		perfil.setUsuario(usuarioLogado);

		// Salva o Perfil
		pr.save(perfil);

		// Cria o dto do Perfil
		PerfilDto perfilDto = new PerfilDto(perfil);

		// Gera o link do novo user adicionado
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(perfilDto.getId())
				.toUri();

		// Envia na resposta o código de criação e no header envie o link gerado do novo
		// perfil
		return ResponseEntity.created(location).build();
	}
}
