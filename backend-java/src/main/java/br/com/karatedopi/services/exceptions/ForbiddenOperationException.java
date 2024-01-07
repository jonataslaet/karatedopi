package br.com.karatedopi.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenOperationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ForbiddenOperationException(String ex) {
        super(ex);
    }
}
