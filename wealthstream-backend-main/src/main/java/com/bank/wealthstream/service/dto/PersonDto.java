package com.bank.wealthstream.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonDto {
    private String idPer;
    private String name;
    private String surname;
    private String gender;
    private int age;
    private String identification;
    private String address;
    private String phone;
}
