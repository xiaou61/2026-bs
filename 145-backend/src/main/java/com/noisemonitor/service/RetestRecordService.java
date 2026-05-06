package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.RetestRecord;
import com.noisemonitor.mapper.RetestRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetestRecordService extends BaseCrudService<RetestRecord> {
    private final RetestRecordMapper inboundRecordMapper;

    @Override
    protected BaseMapper<RetestRecord> mapper() {
        return inboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"inbound_no", "order_no", "consumable_name", "inbound_qty"};
    }
}






