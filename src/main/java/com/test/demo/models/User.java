package com.test.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "tbl_user")
public class User {
    @Id
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "gender", nullable = false)
    private String gender;
    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "username", nullable = false)
    private String username;
    @JsonIgnore
    @Column(name = "password", nullable = false)
    @NotBlank
    private String password;

     @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
}
