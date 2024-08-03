package com.test.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResInvoiceDetailDto {
    private String id;
    private String invoiceId;
    private String productId;
    private String productName;
    private Double price;
    private int qty;
}
