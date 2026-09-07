package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.RepairRecord;
import com.assetrfid.mapper.RepairRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepairRecordService extends BaseCrudService<RepairRecord> {
    private final RepairRecordMapper repairRecordMapper;

    @Override
    protected BaseMapper<RepairRecord> mapper() {
        return repairRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"repair_no", "asset_name", "fault_location", "contact_name"};
    }
}
