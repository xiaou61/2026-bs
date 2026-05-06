package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.ReturnRecord;
import com.assetrfid.mapper.ReturnRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReturnRecordService extends BaseCrudService<ReturnRecord> {
    private final ReturnRecordMapper inboundRecordMapper;

    @Override
    protected BaseMapper<ReturnRecord> mapper() {
        return inboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"inbound_no", "order_no", "consumable_name", "inbound_qty"};
    }
}




