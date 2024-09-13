package com.bank.wealthstream.service.impl.component;

import com.bank.wealthstream.model.Account;
import com.bank.wealthstream.model.enums.AccountType;
import com.bank.wealthstream.model.enums.TypeAccountMovement;
import com.bank.wealthstream.repository.AccountMovementRepository;
import com.bank.wealthstream.repository.AccountRepository;
import com.bank.wealthstream.service.dto.AccountMovementDto;
import com.bank.wealthstream.service.mapper.AccountMapper;
import com.bank.wealthstream.service.mapper.AccountMovementMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionProcessor {
    private final AccountMapper accountMapper;
    private final AccountMovementMapper accountMovementMapper;
    private final AccountRepository accountRepository;
    private final AccountMovementRepository accountMovementRepository;

    public TransactionProcessor(AccountMapper accountMapper, AccountMovementMapper accountMovementMapper,
                                AccountRepository accountRepository, AccountMovementRepository accountMovementRepository) {
        this.accountMapper = accountMapper;
        this.accountMovementMapper = accountMovementMapper;
        this.accountRepository = accountRepository;
        this.accountMovementRepository = accountMovementRepository;
    }

    public AccountMovementDto processTransaction(AccountMovementDto accountMovementDto, String transactionType) {
        Account account = accountRepository.getAccount(accountMovementDto.getIdAcc().getAccountNumber());
        String accountType = account.getAccountType();
        double initialBalance = account.getInitialBalance();
        double transactionValue = accountMovementDto.getValue();

        boolean isValidAmount = false;

        if (isValidTransaction(accountType, initialBalance, transactionValue, transactionType)) {
            isValidAmount = updateAccountAndMovement(account, accountMovementDto, initialBalance, transactionValue, transactionType);
            accountMovementDto.setDate(LocalDateTime.now());
        }

        accountMovementDto.setMovementType(transactionType);

        if (!isValidAmount) {
            return null;
        }

        return accountMovementMapper.accountMovementToAccountMovementDto(accountMovementRepository.save(accountMovementMapper.accountMovementDtoTAccountMovement(accountMovementDto)));
    }

    private boolean isValidTransaction(String accountType, double initialBalance, double transactionValue, String transactionType) {
        return (accountType.trim().toUpperCase().equals(AccountType.AHORROS.toString()) || accountType.trim().toUpperCase().equals(AccountType.CORRIENTE.toString())) &&
                (initialBalance >= transactionValue && transactionType.trim().toUpperCase() != TypeAccountMovement.RETIRO.toString() || initialBalance >= 0);
    }

    private boolean updateAccountAndMovement(Account account, AccountMovementDto accountMovementDto,
                                             double initialBalance, double transactionValue, String transactionType) {
        accountMovementDto.setIdAcc(accountMapper.accountToAccountDto(account));

        double newBalance = 0.0;

        boolean isValid = true;

        if (transactionType.trim().toUpperCase().equals(TypeAccountMovement.DEPOSITO.toString())) {
            newBalance = initialBalance + transactionValue;
        }

        if (transactionType.trim().toUpperCase().equals(TypeAccountMovement.RETIRO.toString())) {
            if (account.getInitialBalance() >= accountMovementDto.getValue()) {
                newBalance = initialBalance - transactionValue;
            } else {
                isValid = false;
            }
        }

        account.setInitialBalance(newBalance);
        accountMovementDto.setValue(transactionValue);
        accountMovementDto.setBalance(newBalance);
        return isValid;
    }
}
