package br.com.karatedopi.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.karatedopi.domain.Usuario;
import br.com.karatedopi.repositories.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService{

	@Autowired
	UsuarioRepository ur;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = ur.findByEmail(username);
		if (!usuario.isPresent()) {
			throw new UsernameNotFoundException("Dados inválidos");
		}
		return usuario.get();
		
	}

}
