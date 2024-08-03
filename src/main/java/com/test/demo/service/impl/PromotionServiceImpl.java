package com.test.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.dto.PromotionDto;
import com.test.demo.dto.SupplierDTO;
import com.test.demo.models.Promotion;
import com.test.demo.repository.PromotionRepository;
import com.test.demo.service.ExtraService;
import com.test.demo.service.PromotionService;

@Service
public class PromotionServiceImpl extends BaseServiceImpl<Promotion, PromotionDto, String>
        implements PromotionService, ExtraService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public JpaRepository<Promotion, String> getRepository() {
        return promotionRepository;
    }

    @Override
    protected PromotionDto convertToDto(Promotion entity) {
        return objectMapper.convertValue(entity, PromotionDto.class);
    }

    @Override
    public void addExtra(String extra) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addExtra'");
    }

}
