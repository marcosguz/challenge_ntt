package com.bank.wealthstream.service;

import com.bank.wealthstream.service.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    CustomerDto updateCustomer(CustomerDto customerDto);
    CustomerDto deleteCustomer(CustomerDto customerDto);
    CustomerDto getCustomerByIdentification(String identification);
    List<CustomerDto> getCustomers();
}
