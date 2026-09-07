package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.DepreciationRecord;
import com.assetrfid.mapper.DepreciationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepreciationRecordService extends BaseCrudService<DepreciationRecord> {
    private final DepreciationRecordMapper depreciationRecordMapper;

    @Override
    protected BaseMapper<DepreciationRecord> mapper() {
        return depreciationRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"depreciation_no", "asset_name", "department_name", "original_value"};
    }
}
