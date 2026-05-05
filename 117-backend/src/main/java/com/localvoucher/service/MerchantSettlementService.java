package com.localvoucher.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.localvoucher.entity.MerchantSettlement;
import com.localvoucher.mapper.MerchantSettlementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantSettlementService extends BaseCrudService<MerchantSettlement> {
    private final MerchantSettlementMapper mapper;

    @Override
    protected BaseMapper<MerchantSettlement> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"settlement_no", "merchant_name", "settlement_cycle", "status"};
    }
}
