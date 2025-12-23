package com.loja_uniformes.admin.utils;

import java.security.SecureRandom;

public class RandomIDGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomID(int length) {
        StringBuilder id = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            id.append(CHARACTERS.charAt(index));
        }
        return id.toString();
    }

    public static void main(String[] args) {
        // Gera um ID de 10 caracteres
        String randomID = generateRandomID(10);
        System.out.println("Random ID: " + randomID);
    }
}
