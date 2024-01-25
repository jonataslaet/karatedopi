package br.com.karatedopi.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum that contains all the Brazilian states.
 * @author Jerônimo Nunes Rocha
 * Edited by Jonatas Blendo dos Santos Laet em 09/05/2021
 */
@Getter
@RequiredArgsConstructor
public enum StateAbbreviation {

	AC(1, 12, "Acre"),
	AL(2, 27, "Alagoas"),
	AM(3, 13, "Amazonas"),
	AP(4, 16, "Amapá"),
	BA(5, 29, "Bahia"),
	CE(6, 23, "Ceará"),
	DF(7, 53, "Distrito Federal"),
	ES(8, 32, "Espírito Santo"),
	GO(9, 52, "Goiás"),
	MA(10, 21, "Maranhão"),
	MG(11, 31, "Minas Gerais"),
	MS(12, 50, "Mato Grosso do Sul"),
	MT(13, 51, "Mato Grosso"),
	PA(14, 15, "Pará"),
	PB(15, 25, "Paraíba"),
	PE(16, 26, "Pernambuco"),
	PI(17, 22, "Piauí"),
	PR(18, 41, "Paraná"),
	RJ(19, 33, "Rio de Janeiro"),
	RN(20, 24, "Rio Grande do Norte"),
	RO(21, 11, "Rondônia"),
	RR(22, 14, "Roraima"),
	RS(23, 43, "Rio Grande do Sul"),
	SC(24, 42, "Santa Catarina"),
	SE(25, 28, "Sergipe"),
	SP(26, 35, "São Paulo"),
	TO(27, 17, "Tocantins");

	private final int id;
	private final int codigoIbge;
	private final String name;

}
