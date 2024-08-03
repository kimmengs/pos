package com.test.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.demo.models.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    
}
