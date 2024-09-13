package com.bank.wealthstream.utils;

import com.bank.wealthstream.model.enums.AccountType;

import java.util.HashMap;
import java.util.Map;

public class AccountCodeUtil {
    private static final Map<String, Integer> usedAccountNumbers = new HashMap<>();

    public static String getBranchCode(String accountType) {
        if (accountType.trim().toUpperCase().equals(AccountType.AHORROS.toString())) {
            return "0022";
        } else if (accountType.trim().toUpperCase().equals(AccountType.CORRIENTE.toString())) {
            return "0055";
        }
        return "";
    }

    public static String getAccountCode(String accountType) {
        if (accountType.trim().toUpperCase().equals(AccountType.AHORROS.toString())) {
            return "22";
        } else if (accountType.trim().toUpperCase().equals(AccountType.CORRIENTE.toString())) {
            return "55";
        }
        return "";
    }

    public static boolean isAccountNumberUsed(String accountNumber) {
        return usedAccountNumbers.containsKey(accountNumber);
    }
}
