package com.bank.wealthstream.utils;

import java.util.stream.IntStream;

public class PositionChangerUtil {
    public static String changePosition(String accountNumber) {
        if (accountNumber.length() != 10) {
            return "Account number must be 10 digits";
        }

        char[] account = accountNumber.toCharArray();
        int start = account.length / 2 - 3;
        int end = account.length / 2 + 2;

        IntStream.range(0, 3)
                .forEach(i -> {
                    char temp = account[start + i];
                    account[start + i] = account[end + i];
                    account[end + i] = temp;
                });
        return new String(account);
    }
}
