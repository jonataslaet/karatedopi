package br.com.karatedopi.configs;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Value("${spring.allowed.origins}")
    private String originsPermittedByCors;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String originFromRequest = req.getHeader("Origin");
        String[] originsPermitted = originsPermittedByCors.split(",");

        if (!isPermitted(originFromRequest, originsPermitted)) {
//            TODO: Uncomment below, because it needs throw an exception saying that the origin from this request need to be valid
//            throw new RuntimeException("Origin from this request neet be valid.");
        }
        res.setHeader("Access-Control-Allow-Origin", originFromRequest);
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setStatus(HttpServletResponse.SC_OK);
        chain.doFilter(request, response);
    }

    private boolean isPermitted(String origin, String[] origins) {
        return Objects.nonNull(origin) && Arrays.stream(origins).anyMatch(currentOrigin ->
                Objects.nonNull(currentOrigin) && currentOrigin.equalsIgnoreCase(origin));
    }
}