package com.loja_uniformes.admin.utils.validator;

import java.util.InputMismatchException;

public class CnpjValidator {

    public static boolean isValidCNPJ(String cnpj) {
        if (cnpj == null) {
            return false;
        }

        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("[^\\d]", "");

        // Verifica se o CNPJ tem exatamente 14 dígitos
        if (cnpj.length() != 14) {
            return false;
        }

        // Verifica se o CNPJ é uma sequência repetida (ex: 00000000000000)
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        try {
            int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int firstSum = 0, secondSum = 0;

            // Calcula o primeiro dígito verificador
            for (int i = 0; i < 12; i++) {
                int num = Character.getNumericValue(cnpj.charAt(i));
                firstSum += num * weight1[i];
                secondSum += num * weight2[i];  // Inclui este passo para o segundo dígito verificador
            }

            int firstVerifyingDigit = firstSum % 11 < 2 ? 0 : 11 - firstSum % 11;
            secondSum += firstVerifyingDigit * weight2[12];  // Adiciona o primeiro dígito verificador ao cálculo do segundo dígito

            int secondVerifyingDigit = secondSum % 11 < 2 ? 0 : 11 - secondSum % 11;

            return firstVerifyingDigit == Character.getNumericValue(cnpj.charAt(12))
                    && secondVerifyingDigit == Character.getNumericValue(cnpj.charAt(13));

        } catch (InputMismatchException e) {
            return false;
        }
    }

}
