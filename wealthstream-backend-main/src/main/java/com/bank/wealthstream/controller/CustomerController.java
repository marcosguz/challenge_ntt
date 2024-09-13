package com.bank.wealthstream.controller;

import com.bank.wealthstream.service.CustomerService;
import com.bank.wealthstream.service.dto.CustomerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create-customer")
    @ResponseBody
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.createCustomer(customerDto), HttpStatus.CREATED);
    }

    @PatchMapping("/update-customer")
    @ResponseBody
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.updateCustomer(customerDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete-customer")
    public ResponseEntity<?> deleteCustomer(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.deleteCustomer(customerDto), HttpStatus.OK);
    }

    @GetMapping("/get-customer/{identification}")
    public ResponseEntity<?> getCustomerByIdentification(@PathVariable String identification) {
        CustomerDto customerDto = customerService.getCustomerByIdentification(identification);

        if (customerDto == null) {
            return new ResponseEntity<>("El cliente no existe", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @GetMapping("/get-all-customer")
    public  ResponseEntity<?> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }
}
