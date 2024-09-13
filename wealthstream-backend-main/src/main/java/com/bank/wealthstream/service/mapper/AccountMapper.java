package com.bank.wealthstream.service.mapper;

import com.bank.wealthstream.model.Account;
import com.bank.wealthstream.service.dto.AccountDto;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {
    AccountDto accountToAccountDto(Account account);
    Account accountDtoToAccount(AccountDto accountDto);
}
