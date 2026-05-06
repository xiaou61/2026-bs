package com.innovationhub.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovationhub.entity.FundingRecord;
import com.innovationhub.mapper.FundingRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FundingRecordService extends BaseCrudService<FundingRecord> {
    private final FundingRecordMapper outboundRecordMapper;

    @Override
    protected BaseMapper<FundingRecord> mapper() {
        return outboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"outbound_no", "consumable_name", "lab_name", "outbound_qty"};
    }
}


