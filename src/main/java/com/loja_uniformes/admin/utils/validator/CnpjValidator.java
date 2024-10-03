package com.loja_uniformes.admin.utils.validator;

import java.util.InputMismatchException;

public class CnpjValidator {

    public static boolean isValidCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) {
            return false;
        }

        // Remove caracteres que não sejam dígitos
        cnpj = cnpj.replaceAll("[^\\d]", "");

        try {
            int[] weight = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int firstSum = 0, secondSum = 0;
            for (int i = 0; i < 12; i++) {
                int num = Character.getNumericValue(cnpj.charAt(i));
                firstSum += num * weight[i + 1];
                secondSum += num * weight[i];
            }

            int firstVerifyingDigit = firstSum % 11 < 2 ? 0 : 11 - firstSum % 11;
            int secondVerifyingDigit = (secondSum + firstVerifyingDigit * weight[0]) % 11 < 2 ? 0 : 11 - (secondSum + firstVerifyingDigit * weight[0]) % 11;

            return firstVerifyingDigit == Character.getNumericValue(cnpj.charAt(12))
                    && secondVerifyingDigit == Character.getNumericValue(cnpj.charAt(13));

        } catch (InputMismatchException e) {
            return false;
        }
    }

}
