package com.test.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.dto.SaleDto;
import com.test.demo.service.SaleService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/sales")
@Validated
public class SaleController {

    @Autowired
    private SaleService saleService;
    
    @PostMapping("/checkout")
    public ResponseEntity<?> checkOut(@Valid @RequestBody SaleDto saleDto) {
        
        return new ResponseEntity<>(saleService.sale(saleDto), HttpStatus.CREATED);
    }
    
}
