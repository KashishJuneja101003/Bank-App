package com.bankapp.util;

import java.security.SecureRandom;

public class AccountNumberGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static String generate13DigitAccountNumber() {
        StringBuilder sb = new StringBuilder();
        sb.append(random.nextInt(9) + 1); // first digit non-zero
        for (int i = 0; i < 12; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
