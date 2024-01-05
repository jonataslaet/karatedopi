package br.com.karatedopi.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceStorageException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceStorageException(String ex) {
        super(ex);
    }
}
