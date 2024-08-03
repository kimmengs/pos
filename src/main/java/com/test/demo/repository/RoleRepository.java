package com.test.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.demo.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}