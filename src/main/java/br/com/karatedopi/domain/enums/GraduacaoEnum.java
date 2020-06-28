package br.com.karatedopi.domain.enums;

public enum GraduacaoEnum {
	
	BRANCA (1, "7º KYU", "Faixa Branca"),
	AMARELA(2, "6º KYU", "Faixa Amarela"),
	VERMELHA(3, "5º KYU", "Faixa Vermelha"),
	LARANJA(4, "4º KYU", "Faixa Laranja"),
	VERDE(5, "3º KYU", "Faixa Verde"),
	ROXA(6, "2º KYU", "Faixa Roxa"),
	MARROM(7, "1º KYU", "Faixa Marrom"),
	PRETA1(1, "1º DAN", "Faixa Marrom"),
	PRETA2(2, "2º DAN", "Faixa Preta"),
	PRETA3(3, "3º DAN", "Faixa Preta"),
	PRETA4(4, "4º DAN", "Faixa Preta"),
	PRETA5(5, "5º DAN", "Faixa Preta"),
	PRETA6(6, "6º DAN", "Faixa Preta"),
	PRETA7(7, "7º DAN", "Faixa Preta"),
	PRETA8(8, "8º DAN", "Faixa Preta"),
	PRETA9(9, "9º DAN", "Faixa Preta"),
	PRETA10(10, "10º DAN", "Faixa Preta");
	
	private final int codigo;
	private final String tipo;
	private final String descricao;

	private GraduacaoEnum(int codigo, String tipo, String descricao) {
		this.codigo = codigo;
		this.tipo = tipo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public String getNome() {
		return descricao;
	}

}
