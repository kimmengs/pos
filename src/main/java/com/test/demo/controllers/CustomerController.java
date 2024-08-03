package com.test.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.models.Customer;
import com.test.demo.service.CustomerService;

import jakarta.validation.Valid;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/customers")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/get")
    public List<Customer> getCustomer(@RequestParam int page, @RequestParam int size) {
        return customerService.getCustomer(page, size);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer) {
        logger.info("Received request to create customer");
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.updateCustomer(customer), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@RequestParam String id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
