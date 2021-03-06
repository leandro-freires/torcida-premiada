package br.com.apptrechos.torcidapremiada.util;

import java.util.InputMismatchException;

public class ValidadorDeCnpj {

	public static boolean isValido(String cnpj) {
		if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") || cnpj.equals("22222222222222")
				|| cnpj.equals("33333333333333") || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
				|| cnpj.equals("66666666666666") || cnpj.equals("77777777777777") || cnpj.equals("88888888888888")
				|| cnpj.equals("99999999999999") || (cnpj.length() != 14)) {
			
			return false;
		}

		char decimoTerceiroDigito, decimoQuartoDigito;
		int soma, i, resultado, numero, peso;
		
		try {
			soma = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				numero = (int) (cnpj.charAt(i) - 48);
				soma = soma + (numero * peso);
				peso = peso + 1;
				if (peso == 10) {
					peso = 2;
				}
			}

			resultado = soma % 11;

			if ((resultado == 0) || (resultado == 1)) {
				decimoTerceiroDigito = '0';
			} else {
				decimoTerceiroDigito = (char) ((11 - resultado) + 48);
			}

			soma = 0;
			peso = 2;

			for (i = 12; i >= 0; i--) {
				numero = (int) (cnpj.charAt(i) - 48);
				soma = soma + (numero * peso);
				peso = peso + 1;
				if (peso == 10) {
					peso = 2;
				}
			}

			resultado = soma % 11;
			if ((resultado == 0) || (resultado == 1)) {
				decimoQuartoDigito = '0';
			} else {
				decimoQuartoDigito = (char) ((11 - resultado) + 48);
			}

			if ((decimoTerceiroDigito == cnpj.charAt(12)) && (decimoQuartoDigito == cnpj.charAt(13))) {
				return true;
			} else {
				return false;
			}
		} catch (InputMismatchException erro) {
			return false;
		}
	}
}
