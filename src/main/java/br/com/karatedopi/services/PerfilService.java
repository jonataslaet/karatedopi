package br.com.karatedopi.services;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.karatedopi.controllers.dtos.PerfilDTO;
import br.com.karatedopi.domain.Perfil;
import br.com.karatedopi.domain.Usuario;
import br.com.karatedopi.repositories.PerfilRepository;
import br.com.karatedopi.repositories.UsuarioRepository;

@Service
public class PerfilService {
	
	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public ResponseEntity<?> cadastrarPerfil(PerfilDTO perfilDTO) {
		Usuario usuario = getUsuarioLogado();
		
		Perfil perfil = new Perfil(perfilDTO);
		perfil.setUsuario(usuario);
		usuario.setPerfil(perfil);
		usuarioRepository.save(usuario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(perfil.getId())
				.toUri();
		return ResponseEntity.created(uri).body(perfilDTO);
	}
	
	public Usuario getUsuarioLogado() {
		Usuario usuarioLogado = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (usuarioLogado == null) {
			throw new RuntimeException("Usuário está deslogado");
		}
		
		return usuarioLogado;
	}

	public ResponseEntity<List<PerfilDTO>> retornarPerfis() {
		List<Perfil> perfis = perfilRepository.findAll();
		if (perfis == null || perfis.isEmpty()) {
			throw new RuntimeException("Nenhum perfil encontrado");
		}
		List<PerfilDTO> perfisDTO = perfis.stream().map(u -> new PerfilDTO(u)).collect(Collectors.toList());
		return ResponseEntity.ok(perfisDTO);
	}
}
