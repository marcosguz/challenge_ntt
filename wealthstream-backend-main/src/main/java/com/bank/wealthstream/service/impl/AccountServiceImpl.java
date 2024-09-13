package com.bank.wealthstream.service.impl;

import com.bank.wealthstream.model.Account;
import com.bank.wealthstream.model.enums.AccountType;
import com.bank.wealthstream.repository.AccountRepository;
import com.bank.wealthstream.service.AccountService;
import com.bank.wealthstream.service.dto.AccountDto;
import com.bank.wealthstream.service.impl.component.AccountNumberGenerator;
import com.bank.wealthstream.service.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private AccountMapper accountMapper;
    private AccountNumberGenerator accountGenerateNumber;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper, AccountNumberGenerator accountGenerateNumber) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.accountGenerateNumber = accountGenerateNumber;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Optional<Account> account = accountRepository.ifExistAccount(
                accountDto.getIdCus().getIdCus(),
                accountDto.getAccountNumber());
        if (account.isPresent()) {
            return null;
        }

        if (accountDto.getAccountType().trim().toUpperCase().equals(AccountType.AHORROS.toString())) {
            accountDto.setAccountType(AccountType.AHORROS.toString());
            accountDto.setAccountNumber(accountGenerateNumber.generateAccountNumber(AccountType.AHORROS.toString()));
        }

        if (accountDto.getAccountType().trim().toUpperCase().equals(AccountType.CORRIENTE.toString())) {
            accountDto.setAccountType(AccountType.CORRIENTE.toString());
            accountDto.setAccountNumber(accountGenerateNumber.generateAccountNumber(AccountType.CORRIENTE.toString()));
        }

        return accountMapper.accountToAccountDto(accountRepository.save(accountMapper.accountDtoToAccount(accountDto)));
    }

    @Override
    public AccountDto deleteAccount(AccountDto accountDto) {
        Account account = accountRepository.getAccount(accountDto.getAccountNumber());
        account.setState(false);
        accountRepository.save(account);
        return accountMapper.accountToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAccountByIdentification(String identification) {
        return accountRepository.getAccountByIdentification(identification).stream()
                .map(accountMapper:: accountToAccountDto)
                .collect(Collectors.toList());
    }
}
