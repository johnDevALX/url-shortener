package com.ekene.serviceurl_shortener.util;

public class Base62 {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateShortUrl() {
        StringBuilder shortUrl = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int index = (int) (Math.random() * ALPHABET.length());
            shortUrl.append(ALPHABET.charAt(index));
        }
        return shortUrl.toString();
    }
}
