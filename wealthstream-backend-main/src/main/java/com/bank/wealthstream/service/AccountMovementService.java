package com.bank.wealthstream.service;

import com.bank.wealthstream.service.dto.AccountMovementDto;
import com.bank.wealthstream.service.dto.TransferDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AccountMovementService {
    List<AccountMovementDto> getAccountMovementByIdentification(String identification);
    AccountMovementDto makeDeposit(AccountMovementDto accountMovementDto);
    AccountMovementDto makeWithdrawal(AccountMovementDto accountMovementDto);
    AccountMovementDto makeTransfer(TransferDto transferDto);
    List<AccountMovementDto> generateReport(LocalDateTime dateInit, LocalDateTime dateFinish);
}
