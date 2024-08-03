package com.test.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.demo.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, String>{
    List<Item> findAllByStatus(String status);
    Item findByStatusOrFullName(String status, String fullName);

}
