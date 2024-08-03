package com.test.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.demo.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
