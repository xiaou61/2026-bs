package com.localvoucher.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.localvoucher.entity.SettlementDetail;
import com.localvoucher.mapper.SettlementDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettlementDetailService extends BaseCrudService<SettlementDetail> {
    private final SettlementDetailMapper mapper;

    @Override
    protected BaseMapper<SettlementDetail> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"detail_no", "settlement_no", "verify_no", "coupon_name"};
    }
}
