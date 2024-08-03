package com.test.demo.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_items")
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @NotBlank
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "description", nullable = true)
    private String description;
    @Min(0)
    private double price;
    @Min(0)
    private int qty;
    @NotBlank
    @Column(name = "category_id", nullable = false)
    private String categoryId;
    @Column(name = "image", nullable = true)
    private String image;
    @NotBlank
    private String size;
    @NotBlank
    private String surgar;
    private String status;
    @Column(name = "created_date", nullable = false)
    private Date createdDate;
}
