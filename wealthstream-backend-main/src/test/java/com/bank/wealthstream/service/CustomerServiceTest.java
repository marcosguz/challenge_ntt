package com.bank.wealthstream.service;

import com.bank.wealthstream.model.Customer;
import com.bank.wealthstream.model.Person;
import com.bank.wealthstream.repository.CustomerRepository;
import com.bank.wealthstream.repository.PersonRepository;
import com.bank.wealthstream.security.Encryption;
import com.bank.wealthstream.service.dto.CustomerDto;
import com.bank.wealthstream.service.dto.PersonDto;
import com.bank.wealthstream.service.impl.CustomerServiceImpl;
import com.bank.wealthstream.service.mapper.CustomerMapper;
import com.bank.wealthstream.service.mapper.PersonMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PersonMapper personMapper;
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Person person;
    private PersonDto personDto;
    private Customer customer;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        person = new Person("be72277c-acf0-4fa9-bcf7-cc352072ee05", "Juan", "Perez", "M", 32, "0852471369", "Carolina", "0963258741");
        personDto = new PersonDto("be72277c-acf0-4fa9-bcf7-cc352072ee05", "Juan", "Perez", "M", 32, "0852471369", "Carolina", "0963258741");
        customer = new Customer("4b63bc7a-319f-40c0-bce4-686ca4884f42", person, "Juan123", "juan@mail.com", true);
        customerDto = new CustomerDto("4b63bc7a-319f-40c0-bce4-686ca4884f42", personDto, "Juan123", "juan@mail.com", true);
    }

    @Test
    void createCustomerIfExitsIdentification() {
        /*Person person = personMapper.personDtoToPerson(customerDto.getPerson());*/
        Mockito.when(personMapper.personDtoToPerson(Mockito.any(PersonDto.class))).thenReturn(person);

        /*Optional<Customer> optionalCustomer = customerRepository.getCustomerByIdentificationOptional(customerDto.getPerson().getIdentification());*/
        Mockito.when(customerRepository.getCustomerByIdentificationOptional(Mockito.anyString())).thenReturn(Optional.of(customer));

        /*if (optionalCustomer.isPresent()) {
            return customerMapper.customerToCustomerDto(optionalCustomer.get());
        }*/
        Mockito.when(customerMapper.customerToCustomerDto(Mockito.any(Customer.class))).thenReturn(customerDto);

        CustomerDto data = customerService.createCustomer(customerDto);

        Assert.isTrue(data.getIdCus() == "4b63bc7a-319f-40c0-bce4-686ca4884f42", "SUCCESS");
    }

    @Test
    void createCustomerIfNotExitsIdentification() {
        /*Person person = personMapper.personDtoToPerson(customerDto.getPerson());*/
        Mockito.when(personMapper.personDtoToPerson(Mockito.any(PersonDto.class))).thenReturn(person);

        /*Optional<Customer> optionalCustomer = customerRepository.getCustomerByIdentificationOptional(customerDto.getPerson().getIdentification());*/
        Mockito.when(customerRepository.getCustomerByIdentificationOptional(Mockito.anyString())).thenReturn(Optional.ofNullable(null));

        /*Person personSave = personRepository.save(person);
        customerDto.setIdCus(personSave.getIdPer());
        customerDto.setPassword(Encryption.aesEncrypt(customerDto.getPassword(), false));
        customerDto.getPerson().setIdPer(personSave.getIdPer());
        CustomerDto customer = customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customerDto)));
        customer.setPerson(personMapper.personToPersonDto(person));*/

        Mockito.when(personRepository.save(Mockito.any(Person.class))).thenReturn(person);

        Mockito.when(customerMapper.customerDtoToCustomer(Mockito.any(CustomerDto.class))).thenReturn(customer);
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
        Mockito.when(customerMapper.customerToCustomerDto(Mockito.any(Customer.class))).thenReturn(customerDto);
        Mockito.when(personMapper.personToPersonDto(Mockito.any(Person.class))).thenReturn(personDto);

        CustomerDto data = customerService.createCustomer(customerDto);

        Assert.isTrue(data.getIdCus() == "be72277c-acf0-4fa9-bcf7-cc352072ee05", "SUCCESS");
    }
}
