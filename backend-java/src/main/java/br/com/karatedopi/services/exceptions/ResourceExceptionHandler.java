package br.com.karatedopi.services.exceptions;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<ValidationError> amazonS3Exception(AmazonS3Exception ex, HttpServletRequest httpServletRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(HttpStatus.BAD_REQUEST.value());
        validationError.setError("Erro de requisição");
        validationError.setMessage(ex.getMessage());
        validationError.setPath(httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(validationError);
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<ValidationError> amazonClientException(AmazonClientException ex, HttpServletRequest httpServletRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(HttpStatus.BAD_REQUEST.value());
        validationError.setError("Erro de requisição");
        validationError.setMessage(ex.getMessage());
        validationError.setPath(httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(validationError);
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<ValidationError> amazonServiceException(AmazonServiceException ex, HttpServletRequest httpServletRequest) {
        Integer errorCode = Integer.parseInt(ex.getErrorCode());
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(errorCode);
        validationError.setError("Erro de requisição");
        validationError.setMessage(ex.getMessage());
        validationError.setPath(httpServletRequest.getRequestURI());

        return ResponseEntity.status(errorCode).body(validationError);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<ValidationError> fileExceptionException(FileException ex, HttpServletRequest httpServletRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(HttpStatus.BAD_REQUEST.value());
        validationError.setError("Erro de requisição");
        validationError.setMessage(ex.getMessage());
        validationError.setPath(httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(validationError);
    }


    @ExceptionHandler(TournamentParticipationException.class)
    public ResponseEntity<StandardError> handleTournamentParticipationException(
            TournamentParticipationException ex, HttpServletRequest httpServletRequest) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.FORBIDDEN.value());
        standardError.setError("Requisito não atendido para participação em torneio");
        standardError.setMessage(ex.getLocalizedMessage());
        standardError.setPath(httpServletRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(standardError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<StandardError> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest httpServletRequest) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.FORBIDDEN.value());
        standardError.setError("Acesso negado");
        standardError.setMessage("O papel deste usuário não permite esta operação");
        standardError.setPath(httpServletRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(standardError);
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<StandardError> handleForbiddenOperationException(ForbiddenOperationException ex, HttpServletRequest httpServletRequest) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.FORBIDDEN.value());
        standardError.setError("Operação não permitida");
        standardError.setMessage(ex.getMessage());
        standardError.setPath(httpServletRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(standardError);
    }

    @ExceptionHandler(ResourceStorageException.class)
    public ResponseEntity<StandardError> handleResourceStorageException(ResourceStorageException ex, HttpServletRequest httpServletRequest) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        standardError.setError("Erro interno do servidor");
        standardError.setMessage(ex.getMessage());
        standardError.setPath(httpServletRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(standardError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest httpServletRequest) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Recurso não encontrado");
        standardError.setMessage(ex.getMessage());
        standardError.setPath(httpServletRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest httpServletRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        validationError.setError("Erro de validação");
        validationError.setMessage(ex.getMessage());
        validationError.setPath(httpServletRequest.getRequestURI());
        for (FieldError fieldError: ex.getBindingResult().getFieldErrors()) {
            validationError.addCustomFieldError(new CustomFieldError(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(validationError);
    }

    @ExceptionHandler(InvalidAuthenticationException.class)
    public ResponseEntity<ValidationError> invalidAuthenticationException(InvalidAuthenticationException ex, HttpServletRequest httpServletRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(HttpStatus.UNAUTHORIZED.value());
        validationError.setError("Erro de autenticação");
        validationError.setMessage(ex.getMessage());
        validationError.setPath(httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(validationError);
    }

    @ExceptionHandler(AlreadyInUseException.class)
    public ResponseEntity<ValidationError> alreadyInUseException(AlreadyInUseException ex, HttpServletRequest httpServletRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(HttpStatus.BAD_REQUEST.value());
        validationError.setError("Recurso já em uso");
        validationError.setMessage(ex.getMessage());
        validationError.setPath(httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(validationError);
    }

    @ExceptionHandler(NoSuchFieldException.class)
    public ResponseEntity<ValidationError> noSuchFieldException(NoSuchFieldException ex, HttpServletRequest httpServletRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(HttpStatus.BAD_REQUEST.value());
        validationError.setError("Campo não encontrado");
        validationError.setMessage(ex.getMessage());
        validationError.setPath(httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(validationError);
    }

}
