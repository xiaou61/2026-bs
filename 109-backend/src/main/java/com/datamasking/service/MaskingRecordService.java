package com.datamasking.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.MaskingRecord;
import com.datamasking.mapper.MaskingRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaskingRecordService extends BaseCrudService<MaskingRecord> {
    private final MaskingRecordMapper mapper;

    @Override
    protected BaseMapper<MaskingRecord> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"record_no", "task_no", "field_name", "status"};
    }

}
