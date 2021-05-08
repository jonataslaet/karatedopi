package br.com.karatedopi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.karatedopi.domain.Usuario;
import br.com.karatedopi.repositories.UsuarioRepository;

@Service
public class AutenticadorService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
		if (!usuarioOptional.isPresent()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		return usuarioOptional.get();
	}

}
