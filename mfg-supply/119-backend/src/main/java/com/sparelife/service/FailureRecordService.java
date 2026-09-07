package com.sparelife.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sparelife.entity.FailureRecord;
import com.sparelife.mapper.FailureRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FailureRecordService extends BaseCrudService<FailureRecord> {
    private final FailureRecordMapper mapper;

    @Override
    protected BaseMapper<FailureRecord> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"failure_no", "equipment_name", "failure_type", "handler_name"};
    }
}
