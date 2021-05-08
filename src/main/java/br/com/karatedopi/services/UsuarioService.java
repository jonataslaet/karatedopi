package br.com.karatedopi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.karatedopi.controllers.dtos.UsuarioDTO;
import br.com.karatedopi.domain.Usuario;
import br.com.karatedopi.repositories.UsuarioRepository;

@Service
public class UsuarioService {

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
	
	
}
