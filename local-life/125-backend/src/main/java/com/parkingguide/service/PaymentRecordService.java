package com.parkingguide.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.PaymentRecord;
import com.parkingguide.mapper.PaymentRecordMapper;
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
        return new String[]{"payment_no", "record_no", "owner_name", "pay_method"};
    }
}
