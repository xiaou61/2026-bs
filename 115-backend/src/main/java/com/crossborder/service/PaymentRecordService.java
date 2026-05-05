package com.crossborder.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossborder.entity.PaymentRecord;
import com.crossborder.mapper.PaymentRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentRecordService extends BaseCrudService<PaymentRecord> {
    private final PaymentRecordMapper mapper;

    @Override
    protected BaseMapper<PaymentRecord> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"payment_no", "bill_no", "pay_channel", "currency_code"};
    }
}
