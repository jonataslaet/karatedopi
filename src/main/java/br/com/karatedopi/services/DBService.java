package br.com.karatedopi.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.karatedopi.domain.Estado;
import br.com.karatedopi.domain.Municipio;
import br.com.karatedopi.domain.Papel;
import br.com.karatedopi.domain.Usuario;
import br.com.karatedopi.domain.enums.EstadoEnum;
import br.com.karatedopi.repositories.EstadoRepository;
import br.com.karatedopi.repositories.PapelRepository;
import br.com.karatedopi.repositories.UsuarioRepository;

@Service
public class DBService {
	
	@Autowired
	private PapelRepository papelRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public boolean instanciaDataBase() {
		
		Papel papelADMIN = new Papel(null, "ROLE_ADMIN");
		Papel papelUSER = new Papel(null, "ROLE_USER");

		Usuario user01 = new Usuario(null, "aluno@gmail.com", encoder.encode("12345"));
		user01.addPapel(papelADMIN);
		user01.addPapel(papelUSER);
		
//		Estado estado01 = new Estado("Piauí", EstadoEnum.PI);
//		Municipio municipio0001 = new Municipio("Teresina");
//		Municipio municipio0002 = new Municipio("Altos");
//		municipio0001.setEstado(estado01);
//		municipio0002.setEstado(estado01);
//		estado01.getMunicipios().addAll(Arrays.asList(municipio0001, municipio0002));
		
//		estadoRepository.saveAll(Arrays.asList(estado01));
		papelRepository.saveAll(Arrays.asList(papelADMIN, papelUSER));
		usuarioRepository.saveAll(Arrays.asList(user01));
		
		return true;
	}

}
