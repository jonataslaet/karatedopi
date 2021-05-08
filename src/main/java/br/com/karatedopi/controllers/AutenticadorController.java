package br.com.karatedopi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karatedopi.controllers.dtos.LoginForm;
import br.com.karatedopi.controllers.dtos.TokenDTO;
import br.com.karatedopi.services.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticadorController {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping
	public ResponseEntity<TokenDTO> autenticar(@RequestBody LoginForm loginForm){
		UsernamePasswordAuthenticationToken dadosDeLogin = new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getSenha());
		try {
			Authentication authenticate = authManager.authenticate(dadosDeLogin);
			String token = tokenService.gerarToken(authenticate);
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
