package com.bank.wealthstream.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountMovementDto {
    private String idMvn;
    private AccountDto idAcc;
    private LocalDateTime date;
    private String movementType;
    private Double value;
    private Double balance;
}
