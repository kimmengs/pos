package com.test.demo.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDto {
    private InvoiceDto invoice;
    private List<InvoiceDetailDto> details;
}