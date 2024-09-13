package com.bank.wealthstream.service.dto;

import com.bank.wealthstream.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {
    private String idCus;
    private PersonDto person;
    private String password;
    private String email;
    private Boolean state;
}
