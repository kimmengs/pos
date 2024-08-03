package com.test.demo.models;

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
@Table(name = "tbl_invoice_detail")
public class InvoiceDetail {
    @Id
    private String id;

    @Column(name = "invoice_id", nullable = false)
    private String invoiceId;

    @Column(name = "product_id", nullable = false)
    @NotBlank(message = "Product id is required")
    private String productId;

    @Column
    @Positive
    private int qty;
}
