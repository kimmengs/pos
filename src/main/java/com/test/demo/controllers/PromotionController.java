package com.test.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.dto.PromotionDto;
import com.test.demo.models.Promotion;
import com.test.demo.service.BaseService;
import com.test.demo.service.PromotionService;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionController extends BaseController<Promotion, PromotionDto, String> {

    @Autowired
    private PromotionService promotionService;

    @Override
    public BaseService<Promotion, PromotionDto, String> getService() {
        return promotionService;
    }

}
