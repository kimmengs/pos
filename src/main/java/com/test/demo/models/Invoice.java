package com.test.demo.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_invoice")
public class Invoice {
    @Id
    private String id;
    @Column(name = "inv_number", nullable = false)
    @NotBlank(message = "Invoice number is required")
    private String invNumber;
    @Column(name = "inv_date", nullable = false)
    private Date invDate;
    @Column(name = "total_amount", nullable = false)
    @Positive
    private Double totalAmount;
    private String status;
    @Column(name = "created_date", nullable = false)
    private Date createdDate;
    @Column(name = "user_id", nullable = false)
    @NotBlank(message = "User id is required")
    private String userId;
}
