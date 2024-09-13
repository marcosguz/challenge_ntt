package com.bank.wealthstream.service.impl;

import com.bank.wealthstream.model.Account;
import com.bank.wealthstream.model.AccountMovement;
import com.bank.wealthstream.model.Customer;
import com.bank.wealthstream.model.enums.AccountType;
import com.bank.wealthstream.model.enums.TypeAccountMovement;
import com.bank.wealthstream.repository.AccountMovementRepository;
import com.bank.wealthstream.repository.AccountRepository;
import com.bank.wealthstream.repository.CustomerRepository;
import com.bank.wealthstream.service.AccountMovementService;
import com.bank.wealthstream.service.dto.AccountMovementDto;
import com.bank.wealthstream.service.dto.TransferDto;
import com.bank.wealthstream.service.impl.component.TransactionProcessor;
import com.bank.wealthstream.service.mapper.AccountMapper;
import com.bank.wealthstream.service.mapper.AccountMovementMapper;
import jakarta.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountMovementServiceImpl implements AccountMovementService {
    private AccountMapper accountMapper;
    private AccountMovementRepository accountMovementRepository;
    private AccountMovementMapper accountMovementMapper;
    private CustomerRepository customerRepository;

    private AccountRepository accountRepository;
    private TransactionProcessor transactionProcessor;

    public AccountMovementServiceImpl(AccountMapper accountMapper, AccountRepository accountRepository,
                                      AccountMovementMapper accountMovementMapper, AccountMovementRepository accountMovementRepository,
                                      CustomerRepository customerRepository, TransactionProcessor transactionProcessor) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
        this.accountMovementMapper = accountMovementMapper;
        this.accountMovementRepository = accountMovementRepository;
        this.customerRepository = customerRepository;
        this.transactionProcessor = transactionProcessor;
    }

    @Override
    public List<AccountMovementDto> getAccountMovementByIdentification(String identification) {
        return accountMovementRepository.getAccountMovementsByIdentification(identification).stream()
                .map(accountMovementMapper::accountMovementToAccountMovementDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountMovementDto makeDeposit(AccountMovementDto accountMovementDto) {
        if (!(accountMovementDto.getMovementType().trim().toUpperCase().equals(TypeAccountMovement.DEPOSITO.toString()))) {
            return null;
        }
        return transactionProcessor.processTransaction(accountMovementDto, accountMovementDto.getMovementType().trim().toUpperCase());
    }

    @Override
    public AccountMovementDto makeWithdrawal(AccountMovementDto accountMovementDto) {
        if (!(accountMovementDto.getMovementType().trim().toUpperCase().equals(TypeAccountMovement.RETIRO.toString()))) {
            return null;
        }
        return transactionProcessor.processTransaction(accountMovementDto, accountMovementDto.getMovementType().trim().toUpperCase());
    }

    @Override
    public AccountMovementDto makeTransfer(TransferDto transferDto) {
        AccountMovementDto accountMovementDto = new AccountMovementDto();
        Account originAccount = accountRepository.getAccount(transferDto.getOriginAccount());
        accountMovementDto.setIdAcc(accountMapper.accountToAccountDto(originAccount));
        accountMovementDto.setValue(transferDto.getValue());

        AccountMovementDto movement = transactionProcessor.processTransaction(accountMovementDto, TypeAccountMovement.RETIRO.toString());

        if (movement == null) {
            return null;
        }

        Account destinationAccount = accountRepository.getAccount(transferDto.getDestinationAccount());
        accountMovementDto.setIdAcc(accountMapper.accountToAccountDto(destinationAccount));

        return transactionProcessor.processTransaction(accountMovementDto, TypeAccountMovement.DEPOSITO.toString());
    }

    @Override
    public List<AccountMovementDto> generateReport(LocalDateTime dateInit, LocalDateTime dateFinish) {
        return accountMovementRepository.getMovements(dateInit, dateFinish).stream()
                .map(accountMovementMapper::accountMovementToAccountMovementDto)
                .collect(Collectors.toList());
    }
}
