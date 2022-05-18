package br.com.karatedopi.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.karatedopi.controllers.dtos.UsuarioDTO;
import br.com.karatedopi.domain.Papel;
import br.com.karatedopi.domain.Usuario;
import br.com.karatedopi.repositories.PapelRepository;
import br.com.karatedopi.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private PapelRepository papelRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCrypter;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public UsuarioDTO buscarUsuario(Long id) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
		if (!usuarioOptional.isPresent()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		return new UsuarioDTO(usuarioOptional.get());
	}

	public ResponseEntity<List<UsuarioDTO>> buscarUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		if (usuarios == null || usuarios.isEmpty()) {
			throw new UsernameNotFoundException("Nenhum usuário encontrado");
		}
		List<UsuarioDTO> usuariosDTO = usuarios.stream().map(u -> new UsuarioDTO(u)).collect(Collectors.toList());
		return ResponseEntity.ok(usuariosDTO);
	}

	public ResponseEntity<?> cadastrarUsuario(UsuarioDTO usuarioDTO) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuarioDTO.getEmail());
		if (usuarioOptional.isPresent()) {
			throw new RuntimeException("Usuário já cadastrado");
		}
		Usuario usuario = new Usuario(usuarioDTO);
		Papel papelDeUsuario = papelRepository.findById(2L).get();
		usuario.setSenha(bCrypter.encode(usuarioDTO.getSenha()));
		usuario.addPapel(papelDeUsuario);
		usuarioRepository.save(usuario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId())
				.toUri();
		return ResponseEntity.created(uri).body(usuarioDTO);
	}
	
	
}
