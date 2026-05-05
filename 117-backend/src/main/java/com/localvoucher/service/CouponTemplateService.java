package com.localvoucher.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.localvoucher.entity.CouponTemplate;
import com.localvoucher.mapper.CouponTemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponTemplateService extends BaseCrudService<CouponTemplate> {
    private final CouponTemplateMapper mapper;

    @Override
    protected BaseMapper<CouponTemplate> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"template_no", "coupon_name", "coupon_type", "merchant_name"};
    }
}
