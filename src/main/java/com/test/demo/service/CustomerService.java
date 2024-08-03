package com.test.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.demo.models.Customer;
import com.test.demo.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Cacheable(value = "customers", key = "#page + '-' + #size")
    public List<Customer> getCustomer(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> cusPage = customerRepository.findAll(pageable);
        return cusPage.getContent();
    }

    @CacheEvict(value = "customers", allEntries = true)
    @CachePut(value = "customerById", key = "#result.id")
    public Customer createCustomer(Customer customer) {
        customer.setId(UUID.randomUUID().toString());
        return customerRepository.save(customer);
    }

    @CacheEvict(value = "customers", allEntries = true)
    @CachePut(value = "customerById", key = "#result.id")
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @CacheEvict(value = { "customers", "customerById" }, key = "#id")
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

}
