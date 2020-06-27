package br.com.karatedopi.controllers.errors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErroDeApi {
	  private HttpStatus status;
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	    private LocalDateTime instante;
	    private String messagem;
	    private String debugMessagem;
	    private List<SubErroDeApi> subErros;

	    private ErroDeApi() {
	        instante = LocalDateTime.now();
	    }

	    public ErroDeApi(HttpStatus status) {
	        this();
	        this.status = status;
	    }

	    public ErroDeApi(HttpStatus status, Throwable ex) {
	        this();
	        this.status = status;
	        this.messagem = "Erro desconhecido e inesperado";
	        this.debugMessagem = ex.getLocalizedMessage();
	    }

	    public ErroDeApi(HttpStatus status, String message, Throwable ex) {
	        this();
	        this.status = status;
	        this.messagem = message;
	        this.debugMessagem = ex.getLocalizedMessage();
	    }

	    public HttpStatus getStatus() {
			return status;
		}

		public void setStatus(HttpStatus status) {
			this.status = status;
		}

		public LocalDateTime getInstante() {
			return instante;
		}

		public void setInstante(LocalDateTime instante) {
			this.instante = instante;
		}

		public String getMessagem() {
			return messagem;
		}

		public void setMessagem(String messagem) {
			this.messagem = messagem;
		}

		public String getDebugMessagem() {
			return debugMessagem;
		}

		public void setDebugMessagem(String debugMessagem) {
			this.debugMessagem = debugMessagem;
		}

		public List<SubErroDeApi> getSubErros() {
			return subErros;
		}

		public void setSubErros(List<SubErroDeApi> subErros) {
			this.subErros = subErros;
		}

		private void adicionaSubErro(SubErroDeApi subErro) {
	        if (subErros == null) {
	            subErros = new ArrayList<>();
	        }
	        subErros.add(subErro);
	    }

	    private void adicionaErroDeValidacao(String objeto, String campo, Object valorRejeitado, String mensagem) {
	        adicionaSubErro(new ErroDeValidacaoDeApi(objeto, campo, valorRejeitado, mensagem));
	    }

	    private void adicionaErroDeValidacao(String object, String message) {
	    	System.out.println("Entrou -> adicionaErroDeValidacao(HttpInputMessage httpInputMessage)");
	        adicionaSubErro(new ErroDeValidacaoDeApi(object, message));
	    }

	    private void adicionaErroDeValidacao(FieldError fieldError) {
	        this.adicionaErroDeValidacao(
	                fieldError.getObjectName(),
	                fieldError.getField(),
	                fieldError.getRejectedValue(),
	                fieldError.getDefaultMessage());
	    }

	    public void adicionaErrosDeValidacao(List<FieldError> fieldErrors) {
	        fieldErrors.forEach(this::adicionaErroDeValidacao);
	    }

	    private void adicionaErroDeValidacao(ObjectError objectError) {
	        this.adicionaErroDeValidacao(
	                objectError.getObjectName(),
	                objectError.getDefaultMessage());
	    }

	    public void adicionaErroDeValidacao(List<ObjectError> errosGlobais) {
	        errosGlobais.forEach(this::adicionaErroDeValidacao);
	    }

	    /**
	     * Método utilizado para adicionar erros de ConstraintViolation. 
	     * Normalmente utilizado quando um @Validated falha.
	     * @param cv the ConstraintViolation
	     */
	    private void adicionaErroDeValidacao(ConstraintViolation<?> cv) {
	    	this.adicionaErroDeValidacao(cv.getRootBeanClass().getSimpleName(), 
	    			((PathImpl)cv.getPropertyPath()).getLeafNode().asString(),
	    			cv.getInvalidValue(),cv.getMessage());
	    }

	    public void adicionaErrosDeValidacao(Set<ConstraintViolation<?>> constraintViolations) {
	        constraintViolations.forEach(this::adicionaErroDeValidacao);
	    }

}
