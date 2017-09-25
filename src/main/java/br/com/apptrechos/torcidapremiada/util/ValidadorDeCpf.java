package br.com.apptrechos.torcidapremiada.util;

import java.util.InputMismatchException;

public class ValidadorDeCpf {

	public static boolean isValido(String cpf) {
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999") || (cpf.length() != 11)) {
			return false;
		}

		char decimoDigito, decimoPrimeiroDigito;
		int soma, resultado, numero, peso;

		try {
			soma = 0;
			peso = 10;

			for (int i = 0; i < 9; i++) {
				numero = (int) (cpf.charAt(i) - 48);
				soma = soma + (numero * peso);
				peso = peso - 1;
			}

			resultado = 11 - (soma % 11);
			if ((resultado == 10) || (resultado == 11))
				decimoDigito = '0';
			else
				decimoDigito = (char) (resultado + 48);

			soma = 0;
			peso = 11;

			for (int i = 0; i < 10; i++) {
				numero = (int) (cpf.charAt(i) - 48);
				soma = soma + (numero * peso);
				peso = peso - 1;
			}

			resultado = 11 - (soma % 11);
			if ((resultado == 10) || (resultado == 11))
				decimoPrimeiroDigito = '0';
			else
				decimoPrimeiroDigito = (char) (resultado + 48);

			if ((decimoDigito == cpf.charAt(9)) && (decimoPrimeiroDigito == cpf.charAt(10)))
				return true;
			else
				return false;
		} catch (InputMismatchException erro) {
			return false;
		}
	}

}
