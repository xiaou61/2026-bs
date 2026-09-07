package com.localvoucher.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.localvoucher.entity.CouponActivity;
import com.localvoucher.mapper.CouponActivityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponActivityService extends BaseCrudService<CouponActivity> {
    private final CouponActivityMapper mapper;

    @Override
    protected BaseMapper<CouponActivity> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"activity_no", "activity_name", "merchant_name", "coupon_name"};
    }
}
