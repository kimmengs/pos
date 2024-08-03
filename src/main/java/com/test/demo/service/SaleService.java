package com.test.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.demo.dto.InvoiceDetailDto;
import com.test.demo.dto.ResInvoiceDetailDto;
import com.test.demo.dto.ResInvoiceDto;
import com.test.demo.dto.ResSaleDto;
import com.test.demo.dto.SaleDto;
import com.test.demo.models.Invoice;
import com.test.demo.models.InvoiceDetail;
import com.test.demo.models.Item;
import com.test.demo.models.User;
import com.test.demo.repository.InvoiceDetailRepository;
import com.test.demo.repository.InvoiceRepository;
import com.test.demo.repository.ItemRepository;
import com.test.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class SaleService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResSaleDto sale(SaleDto saleDto) {

        String invId = UUID.randomUUID().toString();
        double totalAmount = 0.0;
        Invoice invoice = new Invoice(invId, saleDto.getInvoice().getInvNumber(), new Date(),
         0.0, "ACTIVE", new Date(), saleDto.getInvoice().getUserId());
        List<InvoiceDetail> details = new ArrayList<>();

        for (InvoiceDetailDto detail : saleDto.getDetails()) {
            Item item = itemRepository.findById(detail.getProductId()).get();
            totalAmount += (detail.getQty() * item.getPrice());
            details.add(new InvoiceDetail(UUID.randomUUID().toString(), invId, detail.getProductId(), 
            detail.getQty()));
        }
        invoice.setTotalAmount(totalAmount);
        Invoice resultInvoice = invoiceRepository.save(invoice);
        List<InvoiceDetail> resultInvoiceDetails = invoiceDetailRepository.saveAll(details);

        ResSaleDto resSaleDto = new ResSaleDto();
        User user = userRepository.findById(resultInvoice.getUserId()).get();
        resSaleDto.setInvoice(new ResInvoiceDto(resultInvoice.getId(), resultInvoice.getInvNumber(),
         resultInvoice.getInvDate(), resultInvoice.getTotalAmount(), 
        resultInvoice.getStatus(), resultInvoice.getCreatedDate(), resultInvoice.getUserId(), user.getName()));

        List<ResInvoiceDetailDto> resInvoiceDetailDtos = new ArrayList<>();
        for (InvoiceDetail resultInvoiceDetail : resultInvoiceDetails) {
            Item item = itemRepository.findById(resultInvoiceDetail.getProductId()).get();
            resInvoiceDetailDtos.add(new ResInvoiceDetailDto(resultInvoiceDetail.getId(), 
            resultInvoiceDetail.getInvoiceId(), resultInvoiceDetail.getProductId(),
                    item.getFullName(), item.getPrice(), resultInvoiceDetail.getQty()));
        }
        resSaleDto.setDetails(resInvoiceDetailDtos);
        return resSaleDto;
    }
}
