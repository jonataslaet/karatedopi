package br.com.karatedopi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karatedopi.controllers.cadastros.CadastroUsuario;
import br.com.karatedopi.controllers.dtos.UsuarioDto;
import br.com.karatedopi.services.UsuarioService;

@RestController
@RequestMapping(value="/usuarios")
public class UsuarioController {
	@Autowired
	UsuarioService us;
	
	@GetMapping(value ="/{id}")
	public ResponseEntity<UsuarioDto> buscaUsuario(@PathVariable Long id){
		return us.retornaUsuario(id);
	}
	
	// Retorna todos os usuários
	@GetMapping
	public ResponseEntity<List<UsuarioDto>> listaUsuarios(){
		return us.retornaUsuarios();
	}

	// Cadastra um novo usuario
	@PostMapping
	public ResponseEntity<UsuarioDto> criaUsuario(@Valid @RequestBody CadastroUsuario usuario) {
		return us.criarUsuario(usuario);
	}
}
