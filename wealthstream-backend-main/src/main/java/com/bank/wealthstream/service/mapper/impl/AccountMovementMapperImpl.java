package com.bank.wealthstream.service.mapper.impl;

import com.bank.wealthstream.model.AccountMovement;
import com.bank.wealthstream.service.dto.AccountMovementDto;
import com.bank.wealthstream.service.mapper.AccountMapper;
import com.bank.wealthstream.service.mapper.AccountMovementMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMovementMapperImpl implements AccountMovementMapper {
    private AccountMapper accountMapper;

    public AccountMovementMapperImpl(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountMovementDto accountMovementToAccountMovementDto(AccountMovement accountMovement) {
        return new AccountMovementDto(
                accountMovement.getIdMvn(),
                accountMovement.getIdAcc() == null ? null : accountMapper.accountToAccountDto(accountMovement.getIdAcc()),
                accountMovement.getDate(),
                accountMovement.getMovementType(),
                accountMovement.getValue(),
                accountMovement.getBalance()
        );
    }

    @Override
    public AccountMovement accountMovementDtoTAccountMovement(AccountMovementDto accountMovementDto) {
        return new AccountMovement(
                accountMovementDto.getIdMvn(),
                accountMovementDto.getIdAcc() == null ? null : accountMapper.accountDtoToAccount(accountMovementDto.getIdAcc()),
                accountMovementDto.getDate(),
                accountMovementDto.getMovementType(),
                accountMovementDto.getValue(),
                accountMovementDto.getBalance()
        );
    }
}
