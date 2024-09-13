package com.bank.wealthstream.controller;

import com.bank.wealthstream.service.AccountMovementService;
import com.bank.wealthstream.service.dto.AccountMovementDto;
import com.bank.wealthstream.service.dto.TransferDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/account-movement/movement")
@CrossOrigin(origins = "*")
public class AccountMovementController {
    private AccountMovementService accountMovementService;

    public AccountMovementController(AccountMovementService accountMovementService) {
        this.accountMovementService = accountMovementService;
    }

    @PostMapping("/deposit")
    @ResponseBody
    public ResponseEntity<?> makeDeposit(@RequestBody AccountMovementDto accountMovementDto) {
        return new ResponseEntity<>(accountMovementService.makeDeposit(accountMovementDto), HttpStatus.CREATED);
    }

    @PostMapping("/withdrawal")
    @ResponseBody
    public ResponseEntity<?> makeWithdrawal(@RequestBody AccountMovementDto accountMovementDto) {
        AccountMovementDto movement = accountMovementService.makeWithdrawal(accountMovementDto);
        if (movement == null) {
            return new ResponseEntity<>("Saldo Insuficiente", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(movement, HttpStatus.CREATED);
    }

    @PostMapping("/transfer")
    @ResponseBody
    public ResponseEntity<?> maketransfer(@RequestBody TransferDto transferDto) {
        AccountMovementDto movement = accountMovementService.makeTransfer(transferDto);
        if (movement == null) {
            return new ResponseEntity<>("No hay fondos suficiente en la cuenta origen", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(movement, HttpStatus.CREATED);
    }

    @GetMapping("/get-movements-identification/{identification}")
    public ResponseEntity<?> getMovementsByIdentification(@PathVariable String identification) {
        return new ResponseEntity<>(accountMovementService.getAccountMovementByIdentification(identification), HttpStatus.OK);
    }

    @GetMapping("/reports")
    public ResponseEntity<?> generateReportsByDate(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateInit = LocalDate.parse(startDate, formatter);
        LocalDate dateFinish = LocalDate.parse(endDate, formatter);
        LocalDateTime dateTimeInit = dateInit.atStartOfDay();
        LocalDateTime dateTimeFinish = dateFinish.atStartOfDay();
        return new ResponseEntity<>(accountMovementService.generateReport(dateTimeInit, dateTimeFinish), HttpStatus.OK);
    }
}
