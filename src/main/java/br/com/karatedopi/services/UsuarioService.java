package br.com.karatedopi.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.karatedopi.controllers.cadastros.CadastroUsuario;
import br.com.karatedopi.controllers.dtos.UsuarioDto;
import br.com.karatedopi.controllers.errors.ObjectNotFoundException;
import br.com.karatedopi.domain.Usuario;
import br.com.karatedopi.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	UsuarioRepository ur;

	// Retorna um local específico
	public ResponseEntity<UsuarioDto> retornaUsuario(Long id) {
		Optional<Usuario> usuario = ur.findById(id);
		if (!usuario.isPresent()) {
			throw new ObjectNotFoundException("O Usuário não foi encontrado");
		}
		UsuarioDto usuarioDto = new UsuarioDto(usuario.get());
		return ResponseEntity.ok().body(usuarioDto);
	}
		
	// Retorna todos os usuários
	public ResponseEntity<List<UsuarioDto>> retornaUsuarios() {
		List<UsuarioDto> usuariosEncontrados = UsuarioDto.usuariosDto(ur.findAll());
		return ResponseEntity.ok().body(usuariosEncontrados);
	}

	// Cadastra um novo usuario
	public ResponseEntity<UsuarioDto> criarUsuario(CadastroUsuario cadastroUsuario) {

		// Monta o usuário
		Usuario user = new Usuario(cadastroUsuario.getEmail(), cadastroUsuario.getSenha());

		// Salva o usuário
		ur.save(user);

		// Cria o dto do usuário
		UsuarioDto usuarioDto = new UsuarioDto(user);

		// Gera o link do novo user adicionado
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuarioDto.getId())
				.toUri();

		// Envia na resposta o código de criação e no header envie o link gerado do novo
		// user
		return ResponseEntity.created(location).build();
	}
}
