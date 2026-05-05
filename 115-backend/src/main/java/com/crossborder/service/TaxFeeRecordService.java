package com.crossborder.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.crossborder.entity.TaxFeeRecord;
import com.crossborder.mapper.TaxFeeRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxFeeRecordService extends BaseCrudService<TaxFeeRecord> {
    private final TaxFeeRecordMapper mapper;

    @Override
    protected BaseMapper<TaxFeeRecord> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"fee_no", "order_no", "tax_type", "currency_code"};
    }

    @Override
    public void updateStatus(Long id, String status) {
        mapper.update(null, new UpdateWrapper<TaxFeeRecord>().eq("id", id).set("status", status).set("pay_status", status));
    }
}
