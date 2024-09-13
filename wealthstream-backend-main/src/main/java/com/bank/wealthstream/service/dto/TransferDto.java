package com.bank.wealthstream.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferDto {
    private String originAccount;
    private String destinationAccount;
    private double value;
}
