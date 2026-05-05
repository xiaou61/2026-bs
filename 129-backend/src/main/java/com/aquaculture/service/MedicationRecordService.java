package com.aquaculture.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aquaculture.entity.MedicationRecord;
import com.aquaculture.mapper.MedicationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicationRecordService extends BaseCrudService<MedicationRecord> {
    private final MedicationRecordMapper medicationRecordMapper;

    @Override
    protected BaseMapper<MedicationRecord> mapper() {
        return medicationRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"med_no", "pond_no", "medicine_name", "use_date"};
    }
}
