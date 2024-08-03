package com.test.demo.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    @NotBlank(message = "Name is required!")
    private String name;
    @NotBlank(message = "Gender is required!")
    private String gender;
    @Min(value = 10, message = "Age must be greater than 1!")
    private int age;
}
