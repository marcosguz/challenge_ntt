package com.bank.wealthstream.service.impl.component;

import com.bank.wealthstream.utils.AccountCodeUtil;
import com.bank.wealthstream.utils.PositionChangerUtil;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountNumberGenerator {
    public String generateAccountNumber(String accountType) {
        String accountNumber = "";
        Random randNumber = new Random();

        do {
            int randomNumber = 1000 + randNumber.nextInt(9999);
            accountNumber = AccountCodeUtil.getAccountCode(accountType) + AccountCodeUtil.getBranchCode(accountType) + String.format("%04d", randomNumber);
        } while (AccountCodeUtil.isAccountNumberUsed(accountNumber));

        return PositionChangerUtil.changePosition(accountNumber);
    }
}
