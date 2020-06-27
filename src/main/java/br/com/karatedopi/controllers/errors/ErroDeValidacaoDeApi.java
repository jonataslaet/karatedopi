package br.com.karatedopi.controllers.errors;

public class ErroDeValidacaoDeApi extends SubErroDeApi {
	private String objeto;
	private String campo;
	private Object valorRejeitado;
	private String mensagem;

	ErroDeValidacaoDeApi(String objeto, String mensagem) {
		this.objeto = objeto;
		this.mensagem = mensagem;
	}

	public ErroDeValidacaoDeApi(String object, String field, Object rejectedValue, String message) {
		this.objeto = object;
		this.campo = field;
		this.valorRejeitado = rejectedValue;
		this.mensagem = message;
		
	}

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public Object getValorRejeitado() {
		return valorRejeitado;
	}

	public void setValorRejeitado(Object valorRejeitado) {
		this.valorRejeitado = valorRejeitado;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}