package com.localvoucher.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.localvoucher.entity.UserCoupon;
import com.localvoucher.mapper.UserCouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCouponService extends BaseCrudService<UserCoupon> {
    private final UserCouponMapper mapper;

    @Override
    protected BaseMapper<UserCoupon> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"coupon_no", "consumer_name", "coupon_name", "source_channel"};
    }
}
