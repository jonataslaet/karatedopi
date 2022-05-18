package br.com.karatedopi.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.karatedopi.domain.Papel;
import br.com.karatedopi.domain.Usuario;
import br.com.karatedopi.repositories.PapelRepository;
import br.com.karatedopi.repositories.UsuarioRepository;

@Service
public class DBService {
	
	@Autowired
	private PapelRepository papelRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public boolean instanciaDataBase() {
		
		Papel papelADMIN = new Papel(null, "ROLE_ADMIN");
		Papel papelUSER = new Papel(null, "ROLE_USER");

		Usuario user01 = new Usuario(null, "aluno@gmail.com", encoder.encode("12345"));
		user01.addPapel(papelADMIN);
		user01.addPapel(papelUSER);
		
		papelRepository.saveAll(Arrays.asList(papelADMIN, papelUSER));
		usuarioRepository.saveAll(Arrays.asList(user01));
		
		return true;
	}

}
