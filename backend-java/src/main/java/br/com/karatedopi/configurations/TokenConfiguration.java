package br.com.karatedopi.configurations;

import br.com.karatedopi.controllers.dtos.CredentialsDTO;
import br.com.karatedopi.services.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class TokenConfiguration {

    @Value("${security.jwt.duration}")
    private Integer jwtDurationSeconds;

    @Value("${security.jwt.secret}")
    private String secretKey;

    private final UserService userService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(CredentialsDTO credentialsDTO) {
        Date now = new Date();
        Date limitDate = new Date(now.getTime() + jwtDurationSeconds);

        return JWT.create()
                .withSubject(credentialsDTO.email())
                .withIssuedAt(now)
                .withExpiresAt(limitDate)
                .withClaim("email", credentialsDTO.email())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        UserDetails userDetails = userService.loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
