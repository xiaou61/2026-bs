package com.labconsumable.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labconsumable.entity.InboundRecord;
import com.labconsumable.mapper.InboundRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InboundRecordService extends BaseCrudService<InboundRecord> {
    private final InboundRecordMapper inboundRecordMapper;

    @Override
    protected BaseMapper<InboundRecord> mapper() {
        return inboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"inbound_no", "order_no", "consumable_name", "inbound_qty"};
    }
}
