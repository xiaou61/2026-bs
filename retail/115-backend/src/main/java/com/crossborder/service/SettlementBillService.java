package com.crossborder.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.crossborder.entity.SettlementBill;
import com.crossborder.mapper.SettlementBillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettlementBillService extends BaseCrudService<SettlementBill> {
    private final SettlementBillMapper mapper;

    @Override
    protected BaseMapper<SettlementBill> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"bill_no", "merchant_name", "currency_code", "settlement_status"};
    }

    @Override
    public void updateStatus(Long id, String status) {
        mapper.update(null, new UpdateWrapper<SettlementBill>().eq("id", id).set("status", status).set("settlement_status", status));
    }
}
