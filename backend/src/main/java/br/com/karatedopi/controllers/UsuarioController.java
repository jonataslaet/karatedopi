package br.com.karatedopi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karatedopi.controllers.dtos.UsuarioDTO;
import br.com.karatedopi.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/{id}")
	public UsuarioDTO buscarUsuario(@PathVariable("id") Long id){
		return usuarioService.buscarUsuario(id);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<UsuarioDTO>>buscarUsuarios(){
		return usuarioService.buscarUsuarios();
	}
	
	@PostMapping
	public ResponseEntity<?>buscarUsuarios(@RequestBody UsuarioDTO usuarioDTO){
		return usuarioService.cadastrarUsuario(usuarioDTO);
	}
}
