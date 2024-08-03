package com.test.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.demo.models.Promotion;

@Repository
public interface PromotionRepository extends
        JpaRepository<Promotion, String> {

}
