package com.bank.wealthstream.service.mapper;

import com.bank.wealthstream.model.AccountMovement;
import com.bank.wealthstream.service.dto.AccountMovementDto;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMovementMapper {
    AccountMovementDto accountMovementToAccountMovementDto(AccountMovement accountMovement);
    AccountMovement accountMovementDtoTAccountMovement(AccountMovementDto accountMovementDto);
}
