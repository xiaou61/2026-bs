package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.ViolationRecord;
import com.equipmentshare.mapper.ViolationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViolationRecordService extends BaseCrudService<ViolationRecord> {
    private final ViolationRecordMapper violationRecordMapper;

    @Override
    protected BaseMapper<ViolationRecord> mapper() {
        return violationRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"violation_no", "equipment_name", "handler_name", "violation_type"};
    }
}








