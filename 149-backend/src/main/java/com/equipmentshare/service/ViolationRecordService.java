package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.ViolationRecord;
import com.equipmentshare.mapper.ViolationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViolationRecordService extends BaseCrudService<ViolationRecord> {
    private final ViolationRecordMapper inboundRecordMapper;

    @Override
    protected BaseMapper<ViolationRecord> mapper() {
        return inboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"inbound_no", "order_no", "consumable_name", "inbound_qty"};
    }
}








