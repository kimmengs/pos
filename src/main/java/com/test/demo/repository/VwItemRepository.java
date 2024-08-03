package com.test.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.demo.models.VwItem;

public interface VwItemRepository extends JpaRepository<VwItem, String> {
    Page<VwItem> findAllByStatusOrderByCreatedDateDesc(String status, Pageable pageable);

}
