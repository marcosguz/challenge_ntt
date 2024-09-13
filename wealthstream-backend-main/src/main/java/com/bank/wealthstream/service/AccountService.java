package com.bank.wealthstream.service;

import com.bank.wealthstream.service.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto deleteAccount(AccountDto accountDto);
    List<AccountDto> getAccountByIdentification(String identification);
}
