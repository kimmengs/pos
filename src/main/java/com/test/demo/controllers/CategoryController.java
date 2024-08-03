package com.test.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.models.Category;
import com.test.demo.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/get")
    public List<Category> getcCategories() {
        return categoryService.getCategories();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
        return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.updateCategory(category), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@RequestParam String id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
