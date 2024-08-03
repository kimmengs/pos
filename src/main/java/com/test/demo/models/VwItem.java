package com.test.demo.models;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vw_item")
public class VwItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String full_name;
    private String category_name;
    private double price;
    private int qty;
    private String status;
    @Column(name = "created_date")
    private Date createdDate;
}
