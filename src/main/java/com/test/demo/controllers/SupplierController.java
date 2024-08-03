package com.test.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.dto.SupplierDTO;
import com.test.demo.models.Supplier;
import com.test.demo.service.BaseService;
import com.test.demo.service.SupplierService;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController extends BaseController<Supplier, SupplierDTO, String> {

    @Autowired
    private SupplierService supplierService;

    @Override
    public BaseService<Supplier, SupplierDTO, String> getService() {
        return supplierService;
    }

}
