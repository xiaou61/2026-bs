package com.labconsumable.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labconsumable.entity.OutboundRecord;
import com.labconsumable.mapper.OutboundRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutboundRecordService extends BaseCrudService<OutboundRecord> {
    private final OutboundRecordMapper outboundRecordMapper;

    @Override
    protected BaseMapper<OutboundRecord> mapper() {
        return outboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"outbound_no", "consumable_name", "lab_name", "outbound_qty"};
    }
}
