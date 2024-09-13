package com.bank.wealthstream.integrationTest;

import com.bank.wealthstream.WealthstreamApplication;
import com.bank.wealthstream.model.Customer;
import com.bank.wealthstream.model.Person;
import com.bank.wealthstream.repository.CustomerRepository;
import com.bank.wealthstream.repository.PersonRepository;
import com.bank.wealthstream.service.dto.CustomerDto;
import com.bank.wealthstream.service.dto.PersonDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = WealthstreamApplication.class
)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class CustomerIntegration {
    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper mapperJson = new ObjectMapper();
    private CustomerDto customerDto;
    private Customer customer;
    private Person person;



    @BeforeEach
    void setUp() {
        PersonDto personDto;
        person = new Person("be72277c-acf0-4fa9-bcf7-cc352072ee05", "Juan", "Perez", "M", 32, "0852471369", "Carolina", "0963258741");
        personDto = new PersonDto("be72277c-acf0-4fa9-bcf7-cc352072ee05", "Juan", "Perez", "M", 0, "0852471369", "Carolina", "0963258741");
        customer = new Customer("4b63bc7a-319f-40c0-bce4-686ca4884f42", person, "Juan123", "juan@mail.com", true);
        customerDto = new CustomerDto("4b63bc7a-319f-40c0-bce4-686ca4884f42", personDto, "Juan123", "juan@mail.com", true);
    }

    @Test
    void updateCustomerIfAge() throws Exception {
        Mockito.when(customerRepository.findById(Mockito.anyString())).thenReturn(Optional.of(customer));
        Mockito.when(personRepository.findById(Mockito.anyString())).thenReturn(Optional.of(person));
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        mvc.perform(MockMvcRequestBuilders.patch("/api/customer/update-customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperJson.writeValueAsString(customerDto)))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void updateCustomerIfNotAge() throws Exception {
        customerDto.getPerson().setAge(20);
        Mockito.when(customerRepository.findById(Mockito.anyString())).thenReturn(Optional.of(customer));
        Mockito.when(personRepository.findById(Mockito.anyString())).thenReturn(Optional.of(person));

        Mockito.when(personRepository.save(Mockito.any(Person.class))).thenReturn(person);
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        mvc.perform(MockMvcRequestBuilders.patch("/api/customer/update-customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperJson.writeValueAsString(customerDto)))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}
