package com.bank.wealthstream.controller;

import com.bank.wealthstream.service.AccountService;
import com.bank.wealthstream.service.dto.AccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "*")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create-account")
    @ResponseBody
    public ResponseEntity<?> createAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.deleteAccount(accountDto), HttpStatus.OK);
    }

    @GetMapping("/get-account-identification/{identification}")
    public ResponseEntity<?> getAccountByIdentification(@PathVariable String identification) {
        return new ResponseEntity<>(accountService.getAccountByIdentification(identification), HttpStatus.OK);
    }
}
