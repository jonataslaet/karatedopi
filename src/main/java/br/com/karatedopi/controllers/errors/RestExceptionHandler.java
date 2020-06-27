package br.com.karatedopi.controllers.errors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//Quando há uma exceção em algum controller, é aqui que o spring entra
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String erro = "Requisição JSON malformada";
		ErroDeApi erroDeApi = new ErroDeApi(HttpStatus.BAD_REQUEST, erro, ex);
		return construtorDaEntidadeResposta(erroDeApi);
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(ObjectNotFoundException ex) {
		String erro = "Objeto não encontrado";
		ErroDeApi apiError = new ErroDeApi(HttpStatus.NOT_FOUND, erro, ex);
		return construtorDaEntidadeResposta(apiError);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		System.out.println("Entrou em -> protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {");
		String erro = "Erro de validação";
		ErroDeApi apiError = new ErroDeApi(HttpStatus.INTERNAL_SERVER_ERROR, erro, ex.getMostSpecificCause());
		return construtorDaEntidadeResposta(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		System.out.println("Entrou em -> ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,\n" + 
				"			HttpHeaders headers, HttpStatus status, WebRequest request)");
		ErroDeApi apiError = new ErroDeApi(HttpStatus.BAD_REQUEST);
		apiError.setMessagem("Erro de validação");
		apiError.setDebugMessagem(ex.getLocalizedMessage());
		apiError.adicionaErrosDeValidacao(ex.getBindingResult().getFieldErrors());
		apiError.adicionaErroDeValidacao(ex.getBindingResult().getGlobalErrors());
		return construtorDaEntidadeResposta(apiError);
	}
	
	private ResponseEntity<Object> construtorDaEntidadeResposta(ErroDeApi ErroDeApi) {
		return new ResponseEntity<>(ErroDeApi, ErroDeApi.getStatus());
	}
}
