package com.agritrace.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.agritrace.entity.PlantingRecord;
import com.agritrace.mapper.PlantingRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlantingRecordService extends BaseCrudService<PlantingRecord> {
    private final PlantingRecordMapper mapper;

    @Override
    protected BaseMapper<PlantingRecord> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"record_no", "batch_no", "operation_type", "operator_name"};
    }

}
