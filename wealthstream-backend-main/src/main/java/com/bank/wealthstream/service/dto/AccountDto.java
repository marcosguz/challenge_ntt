package com.bank.wealthstream.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDto {
    private String idAcc;
    private CustomerDto idCus;
    private String accountNumber;
    private String accountType;
    private Double initialBalance;
    private Boolean state;
}
