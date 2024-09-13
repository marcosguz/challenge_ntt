package com.bank.wealthstream.service.mapper.impl;

import com.bank.wealthstream.model.Account;
import com.bank.wealthstream.service.dto.AccountDto;
import com.bank.wealthstream.service.mapper.AccountMapper;
import com.bank.wealthstream.service.mapper.CustomerMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapperImpl implements AccountMapper {
    private CustomerMapper customerMapper;

    public AccountMapperImpl(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public AccountDto accountToAccountDto(Account account) {
        return new AccountDto(
                account.getIdAcc(),
                customerMapper.customerToCustomerDto(account.getIdCus()),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getInitialBalance(),
                account.getState()
        );
    }

    @Override
    public Account accountDtoToAccount(AccountDto accountDto) {
        return new Account(
                accountDto.getIdAcc(),
                customerMapper.customerDtoToCustomer(accountDto.getIdCus()),
                accountDto.getAccountNumber(),
                accountDto.getAccountType(),
                accountDto.getInitialBalance(),
                accountDto.getState()
        );
    }
}
