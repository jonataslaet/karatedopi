package br.com.karatedopi.filters;

import br.com.karatedopi.configurations.TokenConfiguration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenConfiguration tokenConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isNotEmptyHeaderAuthorization(header)) {
            String[] fulltoken = header.split(" ");
            if (isBearerToken(fulltoken)) {
                try {
                    setTokenAutentication(fulltoken);
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setTokenAutentication(String[] fulltoken) {
        SecurityContextHolder.getContext().setAuthentication(tokenConfig.validateToken(fulltoken[1]));
    }

    private boolean isNotEmptyHeaderAuthorization(String header) {
        return header != null;
    }

    private boolean isBearerToken(String[] fulltoken) {
        return fulltoken.length == 2 && "Bearer".equals(fulltoken[0]);
    }

}
