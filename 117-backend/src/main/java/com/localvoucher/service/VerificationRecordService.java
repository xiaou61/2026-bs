package com.localvoucher.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.localvoucher.entity.VerificationRecord;
import com.localvoucher.mapper.VerificationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationRecordService extends BaseCrudService<VerificationRecord> {
    private final VerificationRecordMapper mapper;

    @Override
    protected BaseMapper<VerificationRecord> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"verify_no", "coupon_no", "store_name", "cashier_name"};
    }
}
