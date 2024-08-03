package com.test.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.demo.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

}
