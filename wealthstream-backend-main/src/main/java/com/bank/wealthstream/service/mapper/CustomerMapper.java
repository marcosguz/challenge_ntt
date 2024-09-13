package com.bank.wealthstream.service.mapper;

import com.bank.wealthstream.model.Customer;
import com.bank.wealthstream.service.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDto customerToCustomerDto(Customer customer);
    Customer customerDtoToCustomer(CustomerDto customerDto);
}
