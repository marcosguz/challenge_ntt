package com.bank.wealthstream.service.impl;

import com.bank.wealthstream.model.Customer;
import com.bank.wealthstream.model.Person;
import com.bank.wealthstream.repository.CustomerRepository;
import com.bank.wealthstream.repository.PersonRepository;
import com.bank.wealthstream.security.Encryption;
import com.bank.wealthstream.service.CustomerService;
import com.bank.wealthstream.service.dto.CustomerDto;
import com.bank.wealthstream.service.mapper.CustomerMapper;
import com.bank.wealthstream.service.mapper.PersonMapper;
import com.bank.wealthstream.utils.PropertyUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerMapper customerMapper;
    private CustomerRepository customerRepository;
    private PersonMapper personMapper;
    private PersonRepository personRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository, PersonMapper personMapper, PersonRepository personRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
        this.personMapper = personMapper;
        this.personRepository = personRepository;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Person person = personMapper.personDtoToPerson(customerDto.getPerson());
        Optional<Customer> optionalCustomer = customerRepository.getCustomerByIdentificationOptional(customerDto.getPerson().getIdentification());

        if (optionalCustomer.isPresent()) {
            return customerMapper.customerToCustomerDto(optionalCustomer.get());
        }

        Person personSave = personRepository.save(person);
        customerDto.setIdCus(personSave.getIdPer());
        customerDto.setPassword(Encryption.aesEncrypt(customerDto.getPassword(), false));
        customerDto.getPerson().setIdPer(personSave.getIdPer());
        CustomerDto customer = customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customerDto)));
        customer.setPerson(personMapper.personToPersonDto(person));

        return customer;
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Customer existingCustomer = customerRepository.findById(customerDto.getIdCus())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for ID: " + customerDto.getIdCus()));
        Person existingPerson = personRepository.findById(existingCustomer.getIdPer().getIdPer())
                .orElseThrow(() -> new EntityNotFoundException("Person not found for ID: " + existingCustomer.getIdPer().getIdPer()));

        if (customerDto.getPerson().getAge() != 0) {
            existingPerson.setAge(customerDto.getPerson().getAge());
            personRepository.save(existingPerson);
        }

        PropertyUtils.copyNonNullProperties(customerDto.getPerson(), existingPerson);
        PropertyUtils.copyNonNullProperties(customerDto, existingCustomer);

        if (customerDto.getPassword() != null) {
            existingCustomer.setPassword(Encryption.aesEncrypt(customerDto.getPassword(), false));
        }

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.customerToCustomerDto(updatedCustomer);
    }

    @Override
    public CustomerDto deleteCustomer(CustomerDto customerDto) {
        Optional<Customer> customerOptional = customerRepository.findById(customerDto.getIdCus());

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setState(false);
            customerRepository.save(customer);
            return customerMapper.customerToCustomerDto(customer);
        }

        throw new RuntimeException("Cliente no encontrado");
    }


    @Override
    public CustomerDto getCustomerByIdentification(String identification) {
        Customer customer = customerRepository.getCustomerByIdentification(identification);

        if (!(Objects.isNull(customer))) {
            return customerMapper.customerToCustomerDto(customer);
        } else {
            return null;
        }
    }

    @Override
    public List<CustomerDto> getCustomers() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }
}