package com.manager.homework.utils;

import java.util.Random;

public class SubjectUtils {
    public static String makeRandomCode(int length) {
        StringBuilder token = new StringBuilder();
        Random random = new Random();

        while (length-- > 0) {
            int rIndex = random.nextInt(3);
            switch (rIndex) {
                case 0:
                    token.append((char) (random.nextInt(26) + 65));
                    break;
                case 1:
                    token.append((char) (random.nextInt(26) + 97));
                    break;
                case 2:
                    token.append(random.nextInt(10));
                    break;
            }
        }

        return token.toString();
    }
}