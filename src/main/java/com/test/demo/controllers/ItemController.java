package com.test.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.models.Item;
import com.test.demo.service.ItemService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/items")
@Validated
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllItems(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(itemService.getAllItems(page, size), HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getItemById(@RequestParam String id) {
        return new ResponseEntity<>(itemService.getItemById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createItem(@Valid @RequestBody Item item) {
        return new ResponseEntity<>(itemService.createItem(item), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateItem(@Valid @RequestBody Item item) {
        return new ResponseEntity<>(itemService.updateItem(item), HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteItem(@RequestParam String id) {
        itemService.deleteItemById(id);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

}
