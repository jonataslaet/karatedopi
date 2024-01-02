package br.com.karatedopi.configurations;

import br.com.karatedopi.entities.Role;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.services.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

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

    public String createToken(User user) {
        Date now = new Date();
        Date limitDate = new Date(now.getTime() + jwtDurationSeconds);

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(limitDate)
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("status", user.getStatus().toString())
                .withClaim("authorities", user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        UserDetails userDetails = userService.loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getCurrentToken(HttpServletRequest request) {
        String currentToken = "";
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isNotEmptyHeaderAuthorization(header)) {
            String[] fulltoken = header.split(" ");
            if (isBearerToken(fulltoken)) {
                currentToken = (fulltoken[1]);
            }
        }
        return currentToken;
    }

    private boolean isBearerToken(String[] fulltoken) {
        return fulltoken.length == 2 && "Bearer".equals(fulltoken[0]);
    }

    private boolean isNotEmptyHeaderAuthorization(String header) {
        return header != null;
    }

    private void setTokenAuthentication(String[] fulltoken) {
        SecurityContextHolder.getContext().setAuthentication(this.validateToken(fulltoken[1]));
    }

    public void authenticateRequest(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isNotEmptyHeaderAuthorization(header)) {
            String[] fulltoken = header.split(" ");
            if (isBearerToken(fulltoken)) {
                try {
                    setTokenAuthentication(fulltoken);
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }
    }
}
