package com.bank.wealthstream.service.mapper.impl;

import com.bank.wealthstream.model.Customer;
import com.bank.wealthstream.service.dto.CustomerDto;
import com.bank.wealthstream.service.mapper.CustomerMapper;
import com.bank.wealthstream.service.mapper.PersonMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapperImpl implements CustomerMapper {
    private PersonMapper personMapper;

    public CustomerMapperImpl(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }

    @Override
    public CustomerDto customerToCustomerDto(Customer customer) {
        return new CustomerDto(
                customer.getIdCus(),
                customer.getIdPer() == null ? null : personMapper.personToPersonDto(customer.getIdPer()),
                customer.getPassword(),
                customer.getEmail(),
                customer.getState()
        );
    }

    @Override
    public Customer customerDtoToCustomer(CustomerDto customerDto) {
        return new Customer(
                customerDto.getIdCus(),
                customerDto.getPerson() ==  null ? null : personMapper.personDtoToPerson(customerDto.getPerson()),
                customerDto.getPassword(),
                customerDto.getEmail(),
                customerDto.getState()
        );
    }
}
