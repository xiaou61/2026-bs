package com.sparelife.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sparelife.entity.UsageRecord;
import com.sparelife.mapper.UsageRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsageRecordService extends BaseCrudService<UsageRecord> {
    private final UsageRecordMapper mapper;

    @Override
    protected BaseMapper<UsageRecord> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"usage_no", "part_name", "equipment_name", "installer_name"};
    }
}
