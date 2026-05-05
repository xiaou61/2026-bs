package com.carbonmanage.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbonmanage.entity.EmissionRecord;
import com.carbonmanage.mapper.EmissionRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmissionRecordService extends BaseCrudService<EmissionRecord> {
    private final EmissionRecordMapper emissionRecordMapper;

    @Override
    protected BaseMapper<EmissionRecord> mapper() {
        return emissionRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"emission_no", "company_no", "scope_type", "source_name"};
    }
}
