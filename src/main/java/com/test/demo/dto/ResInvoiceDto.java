package com.test.demo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResInvoiceDto {
    private String id;
    private String invNumber;
    private Date invDate;
    private Double totalAmount;
    private String status;
    private Date createdDate;
    private String userId;
    private String userName;
}
