package br.com.karatedopi.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenExpirationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TokenExpirationException(String ex) {
        super(ex);
    }
}
