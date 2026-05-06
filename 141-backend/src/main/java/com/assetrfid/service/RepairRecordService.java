package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.RepairRecord;
import com.assetrfid.mapper.RepairRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepairRecordService extends BaseCrudService<RepairRecord> {
    private final RepairRecordMapper outboundRecordMapper;

    @Override
    protected BaseMapper<RepairRecord> mapper() {
        return outboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"outbound_no", "consumable_name", "lab_name", "outbound_qty"};
    }
}




