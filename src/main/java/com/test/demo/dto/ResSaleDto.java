package com.test.demo.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResSaleDto {
    private ResInvoiceDto invoice;
    private List<ResInvoiceDetailDto> details;
}
