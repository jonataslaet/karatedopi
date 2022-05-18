package br.com.karatedopi.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.karatedopi.domain.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private String expiration;
	
	public String gerarToken(Authentication authentication) {
		Usuario usuario = (Usuario)authentication.getPrincipal();
		Date dataExpiracao = new Date(new Date().getTime()+Long.parseLong(this.expiration));
		
		return Jwts.builder()
				.setIssuer("Karate Tradicional do Piauí")
				.setSubject(usuario.getId().toString())
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS512, this.secret)
				.compact();
	}

	public boolean isValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}
}
