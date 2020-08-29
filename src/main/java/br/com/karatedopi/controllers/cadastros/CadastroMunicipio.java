package br.com.karatedopi.controllers.cadastros;

public class CadastroMunicipio {
	private String descricao;
	private String codigoIBGE;
	private String codigoSIAFI;
	
	public CadastroMunicipio() {
		
	}
	
	public CadastroMunicipio(String descricao, String codigoibge, String codigosiafi) {
		super();
		this.descricao = descricao;
		this.codigoIBGE = codigoibge;
		this.codigoSIAFI = codigosiafi;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigoIBGE() {
		return codigoIBGE;
	}

	public void setCodigoIBGE(String codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}

	public String getCodigoSIAFI() {
		return codigoSIAFI;
	}

	public void setCodigoSIAFI(String codigoSIAFI) {
		this.codigoSIAFI = codigoSIAFI;
	}

	
}
