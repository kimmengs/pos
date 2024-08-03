package com.test.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.dto.SupplierDTO;
import com.test.demo.models.Supplier;
import com.test.demo.repository.SupplierRepository;
import com.test.demo.service.SupplierService;

@Service
public class SupplierServiceImpl extends BaseServiceImpl<Supplier, SupplierDTO, String> 
implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Override
    public JpaRepository getRepository() {
        return supplierRepository;
    }
    @Override
    protected SupplierDTO convertToDto(Supplier entity) {
        return objectMapper.convertValue(entity, SupplierDTO.class);
    }
    @Override
    public List<Supplier> getAll() {
        // TODO Auto-generated method stub
        return super.getAll();
    }

    
}
